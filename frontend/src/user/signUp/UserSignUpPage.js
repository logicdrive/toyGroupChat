import React from 'react';
import TopAppBar from '../../_global/TopAppBar';
import BoldText from '../../_global/text/BoldText';
import NavNavigationButtion from '../../_global/button/IconNavigationButton';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { Card, Stack, TextField, Button } from '@mui/material';

const UserSignUpPage = () => {

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


                    <Button variant="contained" sx={{marginTop: 6, width: 400, marginX: "auto"}}>회원가입</Button>
                    <Button variant="text" sx={{marginTop: 3, marginBottom: 5, width: 400, marginX: "auto"}}>돌아가기</Button>
                </Stack>
            </Card>
        </div>
    )
}

export default UserSignUpPage;