// Nav에 대한 통합적인 버튼 형식

import React from 'react';
import { Link, Button } from '@mui/material';
import NavText from '../text/NavText';

const NavButton = ({children, onClick, sx, buttonSx, textSx, ...props}) => {
    return (
        <Link sx={{backgroundColor: "currentcolor", width: 50, height: 50, ...sx}} {...props}>
            <Button onClick={onClick} sx={{...buttonSx}}>
                <NavText sx={{position: "relative", right: 7, ...textSx}}>
                    {children}
                </NavText>
            </Button>
        </Link>
    );
}

export default NavButton;