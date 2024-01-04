import { React, useState } from 'react';
import { Box, Button } from '@mui/material';
import ImageIcon from '@mui/icons-material/Image';
import ImageUploadButton from './ImageUploadButton';

function App() {
  const [uploadedImageSrc, setUploadedImageSrc] = useState("");
  const onUploadImage = (imageName, imageBase64Url) => {
    console.log(imageName);
    console.log(imageBase64Url);
    
    setUploadedImageSrc(imageBase64Url);
  }

  return (
    <>
    <Box
      component="img"
      sx={{
        height: 150,
        width: 150,
      }}
      alt="업로드된 이미지가 표시됩니다."
      src={uploadedImageSrc}
    />

    <ImageUploadButton onUploadImage={onUploadImage}>
      <Button
          variant="contained"
          color="primary"
          startIcon={<ImageIcon />}
      >
        업로드
      </Button>
    </ImageUploadButton>
    </>
  )
}

export default App;