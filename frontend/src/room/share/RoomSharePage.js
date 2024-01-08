import React, { useContext, useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useNavigate } from "react-router-dom";
import { Card, Stack, Button } from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { AlertPopupContext } from '../../_global/alertPopUp/AlertPopUpContext'
import { JwtTokenContext } from "../../_global/jwtToken/JwtTokenContext";
import TopAppBar from '../../_global/TopAppBar';
import BoldText from '../../_global/text/BoldText';
import NavNavigationButtion from '../../_global/button/IconNavigationButton';
import CollectedDataProxy from '../../_global/proxy/CollectedDataProxy';
import RoomProxy from '../../_global/proxy/RoomProxy';

const RoomSharePage = () => {
    const {addAlertPopUp} = useContext(AlertPopupContext);
    const {jwtTokenState} = useContext(JwtTokenContext);
    const {sharedCode} = useParams();
    const navigate = useNavigate();


    const [roomInfo, setRoomInfo] = useState({});

    useEffect(() => {
        (async () => {
            try {

                setRoomInfo((await CollectedDataProxy.roomBySharedCode(sharedCode, jwtTokenState)))

            } catch (error) {
                addAlertPopUp("코드를 통해서 그룹채팅 정보를 가져오는 과정에서 오류가 발생했습니다!", "error");
                console.error("코드를 통해서 그룹채팅 정보를 가져오는 과정에서 오류가 발생했습니다!", error);
            }
        })()
    }, [addAlertPopUp, jwtTokenState, sharedCode])


    const onClickJoinButton = async () => {
        try {

            await RoomProxy.addRoomUser(sharedCode, jwtTokenState);
            addAlertPopUp("성공적으로 그룹채팅을 추가했습니다.", "success");
            navigate("/room/manage");

        } catch (error) {
            addAlertPopUp("그룹채팅에 참여하는 과정에서 오류가 발생했습니다!", "error");
            console.error("그룹채팅에 참여하는 과정에서 오류가 발생했습니다!", error);
        }
    }


    return (
        <div>
            <TopAppBar title="그룹채팅 참여">
                <NavNavigationButtion url="/room/manage">
                    <ArrowBackIcon sx={{fontSize: 40}}/>
                </NavNavigationButtion>
            </TopAppBar>

            <Card variant="outlined" sx={{ marginTop: 3, textAlign: "center"}}>
                <Stack>
                    <BoldText sx={{fontSize: 25, marginTop: 5}}>{roomInfo.name}</BoldText>

                    <Button onClick={onClickJoinButton} variant="contained" sx={{marginTop: 6, width: 400, marginX: "auto"}}>참여하기</Button>
                    <Button onClick={() => {navigate("/room/manage")}} variant="text" sx={{marginTop: 3, marginBottom: 5, width: 400, marginX: "auto"}}>돌아가기</Button>
                </Stack>
            </Card>
        </div>
    )
}

export default RoomSharePage;