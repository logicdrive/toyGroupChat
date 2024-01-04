import { React, useState } from 'react';
import { Box, Button } from '@mui/material';
import ImageIcon from '@mui/icons-material/Image';
import FileUploadButton from './FileUploadButton';

function App() {
  const [uploadedImageSrc, setUploadedImageSrc] = useState("");
  const onUploadImage = (imageName, imageDataUrl) => {
    console.log(imageName);
    console.log(imageDataUrl);
    
    setUploadedImageSrc(imageDataUrl);
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

    <FileUploadButton accept="image/*" onUploadFile={onUploadImage}>
      <Button
          variant="contained"
          color="primary"
          startIcon={<ImageIcon />}
      >
        업로드
      </Button>
    </FileUploadButton>
    </>
  )
}

export default App;