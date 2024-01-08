import React, { useState } from 'react';
import { Card, CardMedia, Stack, Box, TextField, Button, Dialog, DialogTitle, DialogContent, DialogActions } from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import LinkIcon from '@mui/icons-material/Link';
import AddPhotoAlternateIcon from '@mui/icons-material/AddPhotoAlternate';
import SendIcon from '@mui/icons-material/Send';
import TopAppBar from '../../_global/TopAppBar';
import BoldText from '../../_global/text/BoldText';
import NavButton from '../../_global/button/IconButton';
import NavNavigationButtion from '../../_global/button/IconNavigationButton';
import FileUploadButton from "../../_global/button/FileUploadButton";

const RoomChatPage = () => {
    const [isSharedLinkDialogOpend, setIsSharedLinkDialogOpend] = useState(false);

    const [uploadedImageSrc, setUploadedImageSrc] = useState("");
    const onUploadImage = (imageName, imageDataUrl) => {
      setUploadedImageSrc(imageDataUrl);
    }

    return (
        <div>
            <TopAppBar title="TEST ROOM 1">
                <NavButton sx={{marginRight: 1}} onClick={() => {setIsSharedLinkDialogOpend(true)}}>
                    <LinkIcon sx={{fontSize: 35, paddingTop: 0.3}}/>
                </NavButton>
                
                <NavNavigationButtion url="/room/manage">
                    <ArrowBackIcon sx={{fontSize: 40}}/>
                </NavNavigationButtion>
            </TopAppBar>

            <Dialog open={isSharedLinkDialogOpend} onClose={()=>{setIsSharedLinkDialogOpend(false);}}>
                <DialogTitle sx={{color: "black", fontWeight: "bolder", fontFamily: "BMDfont"}}>공유 링크</DialogTitle>
                <DialogContent>
                    <TextField
                        name="sharedLink"

                        margin="normal"
                        fullWidth

                        sx={{width: 400}}
                        value={"http://sharedlink.com"}
                    />
                </DialogContent>

                <DialogActions>
                    <Button onClick={() => {
                        setIsSharedLinkDialogOpend(false);
                    }} sx={{color: "black", fontWeight: "bolder", fontFamily: "BMDfont"}}>닫기</Button>
                </DialogActions>
            </Dialog>
    

            <Card sx={{marginTop: 3, padding: 1, height: 645}}>
                <Stack>
                    <Box sx={{ padding: 1.5 }}>
                        <Stack>
                            <CardMedia
                                    component="img"
                                    sx={{width: "50px", height: "50px", borderRadius: "25%"}}
                                    image="https://sinsung6722-toygroupchat.s3.ap-northeast-2.amazonaws.com/ProfileImage1.png"
                            />

                            <Box sx={{marginTop: 1}}>
                                <BoldText sx={{padding: 1, backgroundColor: "rgb(236 236 236)", borderRadius: "1rem", display: "inline"}}>
                                    TEST ROOM 1         
                                </BoldText>
                            </Box>
                        </Stack>
                    </Box>
                    <Box sx={{ padding: 1.5 }}>
                        <Stack>
                            <CardMedia
                                    component="img"
                                    sx={{width: "50px", height: "50px", borderRadius: "25%", marginLeft: 57}}
                                    image="https://sinsung6722-toygroupchat.s3.ap-northeast-2.amazonaws.com/ProfileImage2.png"
                            />

                            <Box sx={{marginTop: 1}}>
                                <BoldText sx={{padding: 1, backgroundColor: "rgb(236 236 236)", borderRadius: "1rem", display: "inline", float: "right"}}>
                                    TEST ROOM 1         
                                </BoldText>
                            </Box>
                        </Stack>
                    </Box>
                </Stack>
            </Card>


            <Card sx={{marginTop: 3, height: "auto"}}>
                <FileUploadButton accept="image/*" onUploadFile={onUploadImage}>
                    <NavButton sx={{float: "left", backgroundColor: "white" }} 
                               textSx={((uploadedImageSrc.length === 0) ? {color: "lightgray"} : {color: "black"})}
                               buttonSx={{width: 50, minWidth: 50}}
                    >
                        <AddPhotoAlternateIcon sx={{fontSize: 35, paddingTop: 0.3, paddingLeft: 2}}/>
                    </NavButton>
                </FileUploadButton>

                <TextField
                    name="message"
                    sx={{float: "left", width: 440, height: 50, position: "relative", top: 10}}
                    variant="standard"
                />

                <NavButton sx={{float: "right", backgroundColor: "cornflowerblue"}}>
                    <SendIcon sx={{fontSize: 35, paddingTop: 0.3}}/>
                </NavButton>
            </Card>
        </div>
    )
}

export default RoomChatPage;