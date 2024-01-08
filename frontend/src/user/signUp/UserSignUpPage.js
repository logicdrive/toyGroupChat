import React, { useState } from 'react';
import TopAppBar from '../../_global/TopAppBar';
import BoldText from '../../_global/text/BoldText';
import NavNavigationButtion from '../../_global/button/IconNavigationButton';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import FileUploadButton from "../../_global/button/FileUploadButton";
import ImageIcon from '@mui/icons-material/Image';
import { Card, Stack, TextField, Button, Box } from '@mui/material';

const UserSignUpPage = () => {
    const [uploadedImageSrc, setUploadedImageSrc] = useState("");
    const onUploadImage = (imageName, imageDataUrl) => {
      setUploadedImageSrc(imageDataUrl);
    }


    return (
        <div>
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
                        sx={{marginTop: 10, width: 400, marginX: "auto"}}
                    />

                    <TextField
                        label="비밀번호"
                        name="email"
                        sx={{marginTop: 3, width: 400, marginX: "auto"}}
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
                        src={((uploadedImageSrc.length === 0) ? "/NoImage.jpg" : uploadedImageSrc)}
                    />

                    <FileUploadButton accept="image/*" onUploadFile={onUploadImage}>
                        <Button
                            variant="text"
                            color="primary"
                            startIcon={<ImageIcon />}
                        >
                            프로필 이미지 업로드
                        </Button>
                    </FileUploadButton>


                    <Button variant="contained" sx={{marginTop: 6, width: 400, marginX: "auto"}}>회원가입</Button>
                    <Button variant="text" sx={{marginTop: 3, marginBottom: 5, width: 400, marginX: "auto"}}>돌아가기</Button>
                </Stack>
            </Card>
        </div>
    )
}

export default UserSignUpPage;