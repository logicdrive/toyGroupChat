import { useState, useEffect } from 'react';
import useWebSocket from "react-use-websocket"

const SubscribeSignUpSocket = (notifiedSignUpStatus) => {
  const proxyUrl = `ws://${window.location.host}/socket/collectedData/subscribeSignUp`
  
  const { sendJsonMessage, lastJsonMessage } = useWebSocket(
    proxyUrl,
    {
      share: false,
      shouldReconnect: () => true,
    },
  )

  
  useEffect(() => {
      console.log(`[EFFECT] Notified signUp status by socket: <signUpStatus:${JSON.stringify(lastJsonMessage)}>`)
      if((lastJsonMessage !== null) && lastJsonMessage.userStatus && lastJsonMessage.userId)
        notifiedSignUpStatus(lastJsonMessage.userStatus, lastJsonMessage.userId)
      else
        console.log(`[EFFECT] Ignored Data: ${lastJsonMessage}`)
  }, [lastJsonMessage, notifiedSignUpStatus])


  const subscribeSignUpStatus = useState(() => {
    return (userId) => {
      console.log(`[EFFECT] Subscribe signUp by socket: <url:'${proxyUrl}' userId:${userId}>`)
      sendJsonMessage({"userId": userId})
    }
  })


  return subscribeSignUpStatus;
}

export default SubscribeSignUpSocket;