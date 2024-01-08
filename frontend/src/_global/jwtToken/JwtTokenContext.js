import React, { createContext, useReducer } from 'react';

const initialState = {
    jwtToken: null
};

const REGISTER_TOKEN_VALUE = "REGISTER_TOKEN_VALUE";
const DELETE_TOKEN_VALUE = "DELETE_TOKEN_VALUE";

const reducer = (state, action) => {
    switch (action.type) {
        case REGISTER_TOKEN_VALUE:
            return {
                ...state,
                jwtToken: action.payload
            };
        
        case DELETE_TOKEN_VALUE:
            return {
                ...state,
                jwtToken: null
            };

        default :
            return state;
    }
}


const JwtTokenContext = createContext();

const JwtTokenProvider = ({ children }) => {
    const [jwtTokenState, dispatch] = useReducer(reducer, initialState);


    const registerTokenValue = (jwtToken) => {
        const tokenPayload = JSON.parse(atob(jwtToken.split(".")[1]))
        
        localStorage.setItem("jwtToken", jwtToken);
        dispatch({type: REGISTER_TOKEN_VALUE, payload: {
            value:jwtToken, email:tokenPayload.sub, name:tokenPayload.name, Authorization:`Bearer ${jwtToken}`
        }})
    };
    if((jwtTokenState.jwtToken == null) && (localStorage.getItem("jwtToken") != null))
        registerTokenValue(localStorage.getItem("jwtToken"))

    const deleteTokenValue = () => {
        localStorage.removeItem("jwtToken")
        dispatch({type: DELETE_TOKEN_VALUE, payload:null})
    };


    return (
        <JwtTokenContext.Provider value={{ jwtTokenState, registerTokenValue, deleteTokenValue }}>
            {children}
        </JwtTokenContext.Provider>
    )
}

export { JwtTokenContext, JwtTokenProvider };