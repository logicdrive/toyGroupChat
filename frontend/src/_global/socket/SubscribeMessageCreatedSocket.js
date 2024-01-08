import { useState, useEffect } from 'react';
import useWebSocket from "react-use-websocket"

const SubscribeMessageCreatedSocket = (notifiedMessageCreatedStatus) => {
  const proxyUrl = `ws://${window.location.host}/socket/collectedData/subscribeMessageCreated`
  
  const { sendJsonMessage, lastJsonMessage } = useWebSocket(
    proxyUrl,
    {
      share: false,
      shouldReconnect: () => true,
    },
  )

  
  useEffect(() => {
      console.log(`[EFFECT] Notified created message status by socket: <roomMessageStatus:${JSON.stringify(lastJsonMessage)}>`)
      if((lastJsonMessage !== null) && lastJsonMessage.messageStatus && lastJsonMessage.messageId)
        notifiedMessageCreatedStatus(lastJsonMessage.messageId, lastJsonMessage.messageStatus)
      else
        console.log(`[EFFECT] Ignored Data: ${lastJsonMessage}`)
  }, [lastJsonMessage, notifiedMessageCreatedStatus])


  const subscribeMessageCreatedStatus = useState(() => {
    return (roomId) => {
      console.log(`[EFFECT] Subscribe created Message by socket: <url:'${proxyUrl}' roomId:${roomId}>`)
      sendJsonMessage({"roomId": roomId})
    }
  })


  return subscribeMessageCreatedStatus;
}

export default SubscribeMessageCreatedSocket;