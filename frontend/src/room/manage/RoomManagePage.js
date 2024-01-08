import React, { useState, useContext, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import { Card, Stack, Box, TextField, Button, Dialog, DialogTitle, DialogContent, DialogActions } from '@mui/material';
import LogoutIcon from '@mui/icons-material/Logout';
import GroupAddIcon from '@mui/icons-material/GroupAdd';
import { JwtTokenContext } from "../../_global/jwtToken/JwtTokenContext";
import TopAppBar from '../../_global/TopAppBar';
import BoldText from '../../_global/text/BoldText';
import NavButton from '../../_global/button/IconButton';

const RoomManagePage = () => {
    const { jwtTokenState, deleteTokenValue } = useContext(JwtTokenContext);
    const navigate = useNavigate();
    const [isAddRoomDialogOpend, setIsAddRoomDialogOpend] = useState(false);

    useEffect(() => {
        if(jwtTokenState.jwtToken === null) {
            navigate("/user/signIn");
        }
    }, [jwtTokenState.jwtToken, navigate])

    
    const handleAddRoomSubmit = () => {

    }


    return (
        <div>
            <TopAppBar title="그룹채팅">
                <NavButton sx={{marginRight: 1}} onClick={() => {setIsAddRoomDialogOpend(true);}}>
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
        </div>
    )
}

export default RoomManagePage;