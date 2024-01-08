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
import CollectedDataProxy from '../../_global/proxy/CollectedDataProxy';
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

            const roomId = await RoomProxy.createRoom(inputedRoomName, jwtTokenState);
            subscribeRoomCreaterStatus(roomId)

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
            loadJoinedRooms();
        }
    })

    const [subscribeRoomCreaterStatus] = SubscribeRoomCreaterSocket(notifiedRoomCreaterStatusHandler);



    const [joinedRooms, setJoinedRooms] = useState([]);

    const [loadJoinedRooms] = useState(() => {
        return async () => {
            try {
    
                let modifiedJoinedRooms = await CollectedDataProxy.joinedRooms(jwtTokenState);
                for(let roomIndex=0; roomIndex<modifiedJoinedRooms.length; roomIndex++)
                {
                    let modifiedJoinedRoom = modifiedJoinedRooms[roomIndex];

                    const roomUserInfo = await CollectedDataProxy.roomUsersByRoomId(modifiedJoinedRoom.roomId, jwtTokenState);
                    modifiedJoinedRoom.userCount = roomUserInfo.length;

                    const messagesInfo = await CollectedDataProxy.messagesWithoutCheck(modifiedJoinedRoom.roomId, jwtTokenState);
                    modifiedJoinedRoom.lastestMessage = ((messagesInfo.length === 0) ? "최근 메세지가 없습니다." : messagesInfo[messagesInfo.length-1])
                }

                console.log(modifiedJoinedRooms);
                setJoinedRooms(modifiedJoinedRooms);
    
            } catch (error) {
                addAlertPopUp("참여된 그룹 채팅 목록을 로드하는 도중 에러가 발생했습니다!", "error");
                console.error("참여된 그룹 채팅 목록을 로드하는 도중 에러가 발생했습니다!", error);
            }
        }
    })

    useEffect(() => {
        loadJoinedRooms();
    }, [loadJoinedRooms]);



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
                {
                    joinedRooms.map((joinedRoom, index) => {
                        return  <Card onClick={() => {navigate(`/room/chat/${joinedRoom.roomId}`);}} key={index} variant="outlined" sx={{ padding: 1.5, height: 50, cursor: "pointer" }}>
                            <Stack spacing={1}>
                                <Box>
                                    <BoldText sx={{float: "left"}}>
                                        {joinedRoom.name}
                                    </BoldText>
                                    <BoldText sx={{float: "left", position: "relative", left: 5, color: "gray"}}>
                                        [{joinedRoom.userCount}]
                                    </BoldText>
                                    <BoldText sx={{float: "right", color: "gray"}}>
                                        {new Date(joinedRoom.createdDate).toISOString().replace('T', ' ').slice(0, -5)}
                                    </BoldText>
                                </Box>
                                <Box>
                                    <BoldText sx={{color: "gray"}}>
                                        {joinedRoom.lastestMessage}
                                    </BoldText>
                                </Box>
                            </Stack>
                        </Card>
                    })
                }
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