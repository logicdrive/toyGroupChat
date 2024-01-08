import React from 'react';
import { Container } from "@mui/material";
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AlertPopupProvider } from "./_global/alertPopUp/AlertPopUpContext";
import AlertPopUpList from "./_global/alertPopUp/AlertPopUpList";
import UserSignUpPage from "./user/signUp/UserSignUpPage"
import UserSignInPage from "./user/signIn/UserSignInPage"
import RoomManagePage from "./room/manage/RoomManagePage"
import RoomSharePage from "./room/share/RoomSharePage"
import RoomChatPage from "./room/chat/RoomChatPage"

function App() {
  return (
    <AlertPopupProvider>
      <Container maxWidth="sm">
        <Router>
          <Routes>
                <Route path="/" element={<UserSignInPage/>} />
                <Route path="/user/signUp" element={<UserSignUpPage/>} />
                <Route path="/user/signIn" element={<UserSignInPage/>} />
                <Route path="/room/manage" element={<RoomManagePage/>} />
                <Route path="/room/share" element={<RoomSharePage/>} />
                <Route path="/room/chat" element={<RoomChatPage/>} />
            </Routes>
        </Router>
        <AlertPopUpList/>
      </Container>
    </AlertPopupProvider>
  )
}

export default App;