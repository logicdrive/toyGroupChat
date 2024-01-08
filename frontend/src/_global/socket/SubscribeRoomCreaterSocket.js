import { useState, useEffect } from 'react';
import useWebSocket from "react-use-websocket"

const SubscribeRoomCreaterSocket = (notifiedRoomCreaterStatus) => {
  const proxyUrl = `ws://${window.location.host}/socket/collectedData/subscribeRoomCreater`
  
  const { sendJsonMessage, lastJsonMessage } = useWebSocket(
    proxyUrl,
    {
      share: false,
      shouldReconnect: () => true,
    },
  )

  
  useEffect(() => {
      console.log(`[EFFECT] Notified roomCreater status by socket: <roomCreaterStatus:${JSON.stringify(lastJsonMessage)}>`)
      if((lastJsonMessage !== null) && lastJsonMessage.userId && lastJsonMessage.roomId)
        notifiedRoomCreaterStatus(lastJsonMessage.userId, lastJsonMessage.roomId)
      else
        console.log(`[EFFECT] Ignored Data: ${lastJsonMessage}`)
  }, [lastJsonMessage, notifiedRoomCreaterStatus])


  const subscribeRoomCreaterStatus = useState(() => {
    return (roomId) => {
      console.log(`[EFFECT] Subscribe signUp by socket: <url:'${proxyUrl}' roomId:${roomId}>`)
      sendJsonMessage({"roomId": roomId})
    }
  })


  return subscribeRoomCreaterStatus;
}

export default SubscribeRoomCreaterSocket;