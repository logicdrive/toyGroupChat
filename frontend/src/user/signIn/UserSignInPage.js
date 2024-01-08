import React from 'react';
import TopAppBar from '../../_global/TopAppBar';
import BoldText from '../../_global/text/BoldText';
import { Card, Stack, TextField, Button } from '@mui/material';

const UserSignInPage = () => {

    return (
        <>
            <TopAppBar title="로그인"></TopAppBar>

            <Card variant="outlined" sx={{ marginTop: 3, textAlign: "center"}}>
                <Stack>
                    <BoldText sx={{fontSize: 25, marginTop: 5}}>로그인</BoldText>


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


                    <Button variant="contained" sx={{marginTop: 6, width: 400, marginX: "auto"}}>로그인</Button>
                    <Button variant="text" sx={{marginTop: 3, marginBottom: 5, width: 400, marginX: "auto"}}>회원가입</Button>
                </Stack>
            </Card>
        </>
    )
}

export default UserSignInPage;