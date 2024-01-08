import React, { useContext, useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import { Card, Stack, TextField, Button, Box, Backdrop, CircularProgress } from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import ImageIcon from '@mui/icons-material/Image';
import { AlertPopupContext } from '../../_global/alertPopUp/AlertPopUpContext'
import { JwtTokenContext } from "../../_global/jwtToken/JwtTokenContext";
import TopAppBar from '../../_global/TopAppBar';
import BoldText from '../../_global/text/BoldText';
import NavNavigationButtion from '../../_global/button/IconNavigationButton';
import FileUploadButton from "../../_global/button/FileUploadButton";
import UserProxy from '../../_global/proxy/UserProxy';
import SanityCheckSocket from '../../_global/socket/SanityCheckSocket';
import SubscribeSignUpSocket from '../../_global/socket/SubscribeSignUpSocket';

const UserSignUpPage = () => {
    const {addAlertPopUp} = useContext(AlertPopupContext);
    const { jwtTokenState } = useContext(JwtTokenContext);
    const navigate = useNavigate();
    const [isBackdropOpened, setIsBackdropOpened] = useState(false)
    SanityCheckSocket();

    useEffect(() => {
        if(jwtTokenState.jwtToken !== null) {
            navigate("/room/manage");
        }
    }, [jwtTokenState.jwtToken, navigate])

    const [profileImageSrc, setProfileImageSrc] = useState("");
    const onUploadProfileImage = (imageName, imageDataUrl) => {
        setProfileImageSrc(imageDataUrl);
    }


    const [signUpInfo, setSignUpInfo] = useState({
        "email": "", "password": "", "name": ""
    })

    const handleSignUpInfoChange = (event) => {
        const { name, value } = event.target;
        setSignUpInfo((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    }


    const onSubmitSignUp = async () => {
        setIsBackdropOpened(true);

        try {

            const userData = await UserProxy.signUp(signUpInfo.email, signUpInfo.password, signUpInfo.name, profileImageSrc);
            subscribeSignUpStatus(userData.id);

        } catch(error) {
            addAlertPopUp("회원가입 도중에 오류가 발생했습니다!", "error");
            console.error("회원가입 도중에 오류가 발생했습니다!", error);
            
            setIsBackdropOpened(false);
        }
    }

    const [notifiedSignUpStatusHandler] = useState(() => {
        return (userStatus, userId) => {
            if(userStatus === "SignUpCompleted")
            {
                setIsBackdropOpened(false);
                addAlertPopUp("회원가입이 정상적으로 수행되었습니다.", "success");
                navigate("/user/signIn");
            }
            
            if(userStatus === "UserRemovedByFail")
            {
                addAlertPopUp("회원가입 도중에 오류가 발생했습니다!", "error");
                setIsBackdropOpened(false);
            }
        }
    })

    const [subscribeSignUpStatus] = SubscribeSignUpSocket(notifiedSignUpStatusHandler);


    return (
        <>
            <TopAppBar title="회원가입">
                <NavNavigationButtion url="/user/signIn">
                    <ArrowBackIcon sx={{fontSize: 40}}/>
                </NavNavigationButtion>
            </TopAppBar>

            <Card variant="outlined" sx={{ marginTop: 3, textAlign: "center"}}>
                <Stack>
                    <BoldText sx={{fontSize: 25, marginTop: 5}}>회원가입</BoldText>


                    <TextField
                        label="이메일"
                        name="email"
                        type="email"
                        sx={{marginTop: 10, width: 400, marginX: "auto"}}

                        value={signUpInfo.email}
                        onChange={handleSignUpInfoChange}
                    />

                    <TextField
                        label="비밀번호"
                        name="password"
                        type="password"
                        sx={{marginTop: 3, width: 400, marginX: "auto"}}

                        value={signUpInfo.password}
                        onChange={handleSignUpInfoChange}
                    />

                    <TextField
                        label="닉네임"
                        name="name"
                        type="text"
                        sx={{marginTop: 3, width: 400, marginX: "auto"}}

                        value={signUpInfo.name}
                        onChange={handleSignUpInfoChange}
                    />


                    <Box
                        component="img"
                        sx={{
                            height: 150,
                            width: 150,
                            margin: "auto",
                            backgroundColor: "lightgray",
                            borderRadius: 3,
                            marginTop: 3
                        }}
                        alt="업로드된 이미지가 표시됩니다."
                        src={((profileImageSrc.length === 0) ? "/NoImage.jpg" : profileImageSrc)}
                    />

                    <FileUploadButton accept="image/*" onUploadFile={onUploadProfileImage}>
                        <Button
                            variant="text"
                            color="primary"
                            startIcon={<ImageIcon />}
                        >
                            프로필 이미지 업로드
                        </Button>
                    </FileUploadButton>


                    <Button onClick={onSubmitSignUp} variant="contained" sx={{marginTop: 5, width: 400, marginX: "auto"}}>회원가입</Button>
                    <Button onClick={() => {navigate("/user/signIn")}} variant="text" sx={{marginTop: 3, marginBottom: 2, width: 400, marginX: "auto"}}>돌아가기</Button>
                </Stack>
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

export default UserSignUpPage;