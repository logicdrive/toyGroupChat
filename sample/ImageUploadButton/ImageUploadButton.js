// 이미지 업로드시에 (Base64데이터, 파일명, Base64링크) 형태로 콜백을 일으키기 위해서

import { React, useState, useRef } from 'react';
import { Box } from '@mui/material';

const ImageUploadButton = ({onUploadImage, children, ...props}) => {
    const fileUploadRef = useRef()
  
    const [onUploadedFileChanged] = useState(() => {
      return (e) => {
        if(!(e.target.files[0])) return;
  
        const reader = new FileReader();
        reader.addEventListener("load", () => {
            onUploadImage(reader.result.split(",")[1], e.target.files[0].name, reader.result)
          },
          false,
        );
  
        reader.readAsDataURL(e.target.files[0]);
      }
    });
  
    return (
      <>
      <form>
        <input 
          accept="image/*"
          type="file"
          ref={fileUploadRef}
          onChange={onUploadedFileChanged}
          style={{opacity: 0, width: 0, height: 0}}
        />
  
        <Box
          onClick={() => {fileUploadRef.current.click()}}
          {...props}
        >
          {children}
        </Box>
      </form>
      </>
    )
}

export default ImageUploadButton;