import { useEffect } from 'react';
import useWebSocket, { ReadyState } from "react-use-websocket"

const SanityCheckSocket = () => {
  const proxyUrl = `ws://${window.location.host}/socket/collectedData/sanityCheck`

  const { sendJsonMessage, lastJsonMessage, readyState } = useWebSocket(
    proxyUrl,
    {
      share: false,
      shouldReconnect: () => true,
    },
  )


  useEffect(() => {
    if (readyState === ReadyState.OPEN) {
        const sendMessage = {"message": "Hello, World !"}
        console.log(`[EFFECT] Send message for sanityCheck by using socket: <url:'${proxyUrl}' sendMessage:${JSON.stringify(sendMessage)}>`)
        sendJsonMessage(sendMessage)
    }
  }, [proxyUrl, readyState, sendJsonMessage])

  useEffect(() => {
    if((lastJsonMessage == null) || (lastJsonMessage.length === 0)) return;

    console.log(`[EFFECT] Got message from sanityCheck socket server: <lastJsonMessage:${JSON.stringify(lastJsonMessage)}>`)
  }, [lastJsonMessage])
}

export default SanityCheckSocket;