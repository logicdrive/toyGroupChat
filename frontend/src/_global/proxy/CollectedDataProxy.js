import axios from 'axios';

class CollectedDataProxy {
    static async joinedRooms(jwtTokenState) {
        console.log(`[EFFECT] joinedRooms`)
        
        const requestHeader = {headers: {Authorization: jwtTokenState.jwtToken.Authorization}};
        const response = await axios.get(`http://${window.location.host}/api/collectedData/rooms/joinedRooms`, requestHeader);
        
        console.log(response)
        return response.data.rooms
    }
}

export default CollectedDataProxy