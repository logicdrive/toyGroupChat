import axios from 'axios';

class UserProxy {
    static async signUp(email, password, name, dataUrl) {
        console.log(`[EFFECT] signUp : <email:${email}, password:${password}, name:${name}, dataUrlLength:${dataUrl.length}>`)
        
        const reqDto = {
            "email": email,
            "password": password,
            "name": name,
            "dataUrl": dataUrl
        }
        const response = await axios.put(`http://${window.location.host}/api/user/users/signUp`, reqDto);
        
        console.log(response)
        return response.data
    }
}

export default UserProxy