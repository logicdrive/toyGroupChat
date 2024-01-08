import React from 'react';
import TopAppBar from '../../_global/TopAppBar';
import BoldText from '../../_global/text/BoldText';
import NavNavigationButtion from '../../_global/button/NavNavigationButton';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { Card, Stack, Button } from '@mui/material';

const RoomSharePage = () => {

    return (
        <div>
            <TopAppBar title="그룹채팅 참여">
                <NavNavigationButtion url="/room/manage">
                    <ArrowBackIcon sx={{fontSize: 40}}/>
                </NavNavigationButtion>
            </TopAppBar>

            <Card variant="outlined" sx={{ marginTop: 3, textAlign: "center"}}>
                <Stack>
                    <BoldText sx={{fontSize: 25, marginTop: 5}}>TEST ROOM 1</BoldText>

                    <Button variant="contained" sx={{marginTop: 6, width: 400, marginX: "auto"}}>참여하기</Button>
                    <Button variant="text" sx={{marginTop: 3, marginBottom: 5, width: 400, marginX: "auto"}}>돌아가기</Button>
                </Stack>
            </Card>
        </div>
    )
}

export default RoomSharePage;