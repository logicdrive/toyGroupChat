import React, { useState, useContext, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import { Card, Stack, Box, TextField, Button, Dialog, DialogTitle, DialogContent, DialogActions,
         Backdrop, CircularProgress } from '@mui/material';
import LogoutIcon from '@mui/icons-material/Logout';
import GroupAddIcon from '@mui/icons-material/GroupAdd';
import { AlertPopupContext } from '../../_global/alertPopUp/AlertPopUpContext'
import { JwtTokenContext } from "../../_global/jwtToken/JwtTokenContext";
import TopAppBar from '../../_global/TopAppBar';
import BoldText from '../../_global/text/BoldText';
import NavButton from '../../_global/button/IconButton';
import RoomProxy from '../../_global/proxy/RoomProxy';
import SubscribeRoomCreaterSocket from '../../_global/socket/SubscribeRoomCreaterSocket';

const RoomManagePage = () => {
    const {addAlertPopUp} = useContext(AlertPopupContext);
    const {jwtTokenState, deleteTokenValue} = useContext(JwtTokenContext);
    const navigate = useNavigate();
    const [isBackdropOpened, setIsBackdropOpened] = useState(false)
    const [isAddRoomDialogOpend, setIsAddRoomDialogOpend] = useState(false);

    useEffect(() => {
        if(jwtTokenState.jwtToken === null) {
            navigate("/user/signIn");
        }
    }, [jwtTokenState.jwtToken, navigate])


    const [inputedRoomName, setInputedRoomName] = useState("")
    
    const handleAddRoomSubmit = async () => {
        setIsBackdropOpened(true);

        try {

            const roomInfo = await RoomProxy.createRoom(inputedRoomName, jwtTokenState);
            subscribeRoomCreaterStatus(roomInfo.id)

        } catch(error) {
            addAlertPopUp("그룹채팅 추가 도중에 오류가 발생했습니다!", "error");
            console.error("그룹채팅 추가 도중에 오류가 발생했습니다!", error);
            
            setIsBackdropOpened(false);
        }
    }

    const [notifiedRoomCreaterStatusHandler] = useState(() => {
        return (userId, roomId) => {
            addAlertPopUp("그륩채팅 생성이 정상적으로 수행되었습니다.", "success");
            setIsBackdropOpened(false);
        }
    })

    const [subscribeRoomCreaterStatus] = SubscribeRoomCreaterSocket(notifiedRoomCreaterStatusHandler);


    return (
        <>
            <TopAppBar title="그룹채팅">
                <NavButton sx={{marginRight: 1}} onClick={() => {setInputedRoomName("");setIsAddRoomDialogOpend(true);}}>
                    <GroupAddIcon sx={{fontSize: 35, paddingTop: 0.3}}/>
                </NavButton>
                
                <NavButton sx={{marginRight: 1}} onClick={() => {deleteTokenValue();}}>
                    <LogoutIcon sx={{fontSize: 35, paddingTop: 0.3, paddingLeft: 0.3}}/>
                </NavButton>
            </TopAppBar>

            <Dialog open={isAddRoomDialogOpend} onClose={()=>{setIsAddRoomDialogOpend(false);}}>
                <DialogTitle sx={{color: "black", fontWeight: "bolder", fontFamily: "BMDfont"}}>그룹채팅 추가</DialogTitle>
                <DialogContent>
                    <TextField
                        label="그룹채팅명"
                        name="roomName"

                        margin="normal"
                        fullWidth

                        value={inputedRoomName}
                        onChange={(e) => {setInputedRoomName(e.target.value)}}
                    />
                </DialogContent>

                <DialogActions>
                    <Button onClick={() => {
                        handleAddRoomSubmit();
                        setIsAddRoomDialogOpend(false);
                    }} sx={{color: "black", fontWeight: "bolder", fontFamily: "BMDfont"}}>추가</Button>
                </DialogActions>
            </Dialog>

            <Stack spacing={1} sx={{marginTop: 3}}>
                <Card variant="outlined" sx={{ padding: 1.5, height: 50 }}>
                    <Stack spacing={1}>
                        <Box>
                            <BoldText sx={{float: "left"}}>
                                TEST ROOM 1
                            </BoldText>
                            <BoldText sx={{float: "left", position: "relative", left: 5, color: "gray"}}>
                                [32]
                            </BoldText>
                            <BoldText sx={{float: "right", color: "gray"}}>
                                2013.03.09
                            </BoldText>
                        </Box>
                        <Box>
                            <BoldText sx={{color: "gray"}}>
                                TEST MESSAGE
                            </BoldText>
                        </Box>
                    </Stack>
                </Card>

                <Card variant="outlined" sx={{ padding: 1.5, height: 50 }}>
                    <Stack spacing={1}>
                        <Box>
                            <BoldText sx={{float: "left"}}>
                                TEST ROOM 1
                            </BoldText>
                            <BoldText sx={{float: "left", position: "relative", left: 5, color: "gray"}}>
                                [32]
                            </BoldText>
                            <BoldText sx={{float: "right", color: "gray"}}>
                                2013.03.09
                            </BoldText>
                        </Box>
                        <Box>
                            <BoldText sx={{color: "gray"}}>
                                TEST MESSAGE
                            </BoldText>
                        </Box>
                    </Stack>
                </Card>
            </Stack>

            <Backdrop
                sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
                open={isBackdropOpened}
            >
                <CircularProgress color="inherit" />
            </Backdrop>
        </>
    )
}

export default RoomManagePage;