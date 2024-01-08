import React, { useState, useContext, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Card, CardMedia, Stack, Box, TextField, Button, Dialog, DialogTitle, DialogContent, DialogActions,
         Backdrop, CircularProgress } from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import LinkIcon from '@mui/icons-material/Link';
import AddPhotoAlternateIcon from '@mui/icons-material/AddPhotoAlternate';
import SendIcon from '@mui/icons-material/Send';
import { AlertPopupContext } from '../../_global/alertPopUp/AlertPopUpContext'
import { JwtTokenContext } from "../../_global/jwtToken/JwtTokenContext";
import TopAppBar from '../../_global/TopAppBar';
import BoldText from '../../_global/text/BoldText';
import NavButton from '../../_global/button/IconButton';
import NavNavigationButtion from '../../_global/button/IconNavigationButton';
import FileUploadButton from "../../_global/button/FileUploadButton";
import CollectedDataProxy from '../../_global/proxy/CollectedDataProxy';
import MessageProxy from '../../_global/proxy/MessageProxy';

const RoomChatPage = () => {
    const {addAlertPopUp} = useContext(AlertPopupContext);
    const {jwtTokenState} = useContext(JwtTokenContext);
    const {roomId} = useParams();
    const [isSharedLinkDialogOpend, setIsSharedLinkDialogOpend] = useState(false);
    const [isBackdropOpened, setIsBackdropOpened] = useState(false)

    const [uploadedImageSrc, setUploadedImageSrc] = useState("");
    const [uploadedImageName, setUploadedImageName] = useState("");
    const [inputedContent, setInputedContent] = useState("");
    const onUploadImage = (imageName, imageDataUrl) => {
      setUploadedImageSrc(imageDataUrl);
      setUploadedImageName(imageName);
    }

    const handleMessageSubmit = async () => {
        setIsBackdropOpened(true);

        try {

            const messageId = await MessageProxy.createMessage(roomId, inputedContent, uploadedImageName, uploadedImageSrc,  jwtTokenState);
            console.log(messageId);
            
            setUploadedImageSrc("");
            setUploadedImageName("");
            setInputedContent("");
            
            setIsBackdropOpened(false);

        } catch(error) {
            addAlertPopUp("메세지 추가 도중에 오류가 발생했습니다!", "error");
            console.error("메세지 추가 도중에 오류가 발생했습니다!", error);
            
            setIsBackdropOpened(false);
        }
    }


    const [roomInfo, setRoomInfo] = useState({});

    useEffect(() => {
        (async () => {
            try {

                setRoomInfo((await CollectedDataProxy.roomByRoomId(roomId, jwtTokenState)))

            } catch (error) {
                addAlertPopUp("그룹 채팅 정보를 가져오는 과정에서 오류가 발생했습니다!", "error");
                console.error("그룹 채팅 정보를 가져오는 과정에서 오류가 발생했습니다!", error);
            }
        })()
    }, [addAlertPopUp, jwtTokenState, roomId])


    const [roomMessages, setRoomMessages] = useState([]);
    const [loadRoomMessages] = useState(() => {
        return async () => {
            try {
    
                let modifiedRoomMessages = await CollectedDataProxy.messagesWithCheck(roomId, jwtTokenState);
                for(let messageIndex=0; messageIndex<modifiedRoomMessages.length; messageIndex++)
                {
                    let modifiedRoomMessage = modifiedRoomMessages[messageIndex];

                    const resUserInfo = await CollectedDataProxy.findByUserId(modifiedRoomMessage.userId, jwtTokenState);
                    modifiedRoomMessage.profileImageFileId = resUserInfo.profileImageFileId;
                    modifiedRoomMessage.userName = resUserInfo.name;
                    modifiedRoomMessage.userEmail = resUserInfo.email;

                    const resProfileFileInfo = await CollectedDataProxy.findByFileId(modifiedRoomMessage.profileImageFileId, jwtTokenState);
                    modifiedRoomMessage.profileImageFileUrl = resProfileFileInfo.url;

                    
                    if(modifiedRoomMessage.fileId !== null) {
                        const resFileInfo = await CollectedDataProxy.findByFileId(modifiedRoomMessage.fileId, jwtTokenState);
                        modifiedRoomMessage.fileUrl = resFileInfo.url; 
                        modifiedRoomMessage.fileName = resFileInfo.name;
                    }
                }

                console.log(modifiedRoomMessages);
                setRoomMessages(modifiedRoomMessages);
    
            } catch (error) {
                addAlertPopUp("그룹 채팅 메세지들을 로드하는 도중 에러가 발생했습니다!", "error");
                console.error("그룹 채팅 메세지들을 로드하는 도중 에러가 발생했습니다!", error);
            }
        }
    })

    useEffect(() => {
        loadRoomMessages();
    }, [loadRoomMessages]);


    return (
        <>
            <TopAppBar title={roomInfo.name}>
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
                        value={`http://${window.location.host}/room/share/${roomInfo.sharedCode}`}
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

                    {
                        roomMessages.map((roomMessage, index) => {
                            if(jwtTokenState.jwtToken.id === roomMessage.userId)
                            {
                                return (
                                    <Box key={index} sx={{ padding: 1.5 }}>
                                        <Stack>
                                            <Box>
                                                <CardMedia
                                                        component="img"
                                                        sx={{width: "50px", height: "50px", borderRadius: "25%", float: "right"}}
                                                        image={roomMessage.profileImageFileUrl}
                                                />
                                                <BoldText sx={{float: "right", marginTop: 3.5, marginLeft: 1}}>
                                                    {roomMessage.userName}
                                                </BoldText>
                                            </Box>
    
                                            {
                                                (roomMessage.fileUrl !== undefined) ? (
                                                    <CardMedia
                                                            component="img"
                                                            sx={{width: "250px", height: "200px", borderRadius: "5%", marginLeft: 32.5}}
                                                            image={roomMessage.fileUrl}
                                                    />
                                                ) : null
                                            }
                
                                            <Box sx={{marginTop: 1}}>
                                                <BoldText sx={{padding: 1, backgroundColor: "rgb(236 236 236)", borderRadius: "1rem", display: "inline", float: "right"}}>
                                                    {roomMessage.content}         
                                                </BoldText>
                                            </Box>
                                        </Stack>
                                    </Box>
                                )
                            }
                            else {
                                return (
                                    <Box key={index} sx={{ padding: 1.5 }}>
                                        <Stack>
                                            <Box>
                                                <CardMedia
                                                        component="img"
                                                        sx={{width: "50px", height: "50px", borderRadius: "25%", float: "left"}}
                                                        image={roomMessage.profileImageFileUrl}
                                                />
                                                <BoldText sx={{float: "left", marginTop: 3.5, marginLeft: 1}}>
                                                    {roomMessage.userName}
                                                </BoldText>
                                            </Box>

                                            {
                                                (roomMessage.fileUrl !== undefined) ? (
                                                    <CardMedia
                                                            component="img"
                                                            sx={{width: "250px", height: "200px", borderRadius: "5%"}}
                                                            image={roomMessage.fileUrl}
                                                    />
                                                ) : null
                                            }
                
                                            <Box sx={{marginTop: 1}}>
                                                <BoldText sx={{padding: 1, backgroundColor: "rgb(236 236 236)", borderRadius: "1rem", display: "inline"}}>
                                                    {roomMessage.content}         
                                                </BoldText>
                                            </Box>
                                        </Stack>
                                    </Box>
                                )
                            }
                        })
                    }
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

                    value={inputedContent}
                    onChange={(e) => {setInputedContent(e.target.value)}}
                />

                <NavButton onClick={handleMessageSubmit} sx={{float: "right", backgroundColor: "cornflowerblue"}}>
                    <SendIcon sx={{fontSize: 35, paddingTop: 0.3}}/>
                </NavButton>
            </Card>


            <Backdrop
                sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
                open={isBackdropOpened}
            >
                <CircularProgress color="inherit" />
            </Backdrop>
        </>
    )
}

export default RoomChatPage;