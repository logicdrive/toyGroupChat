// 파일 업로드시에 (fileName, dataUrl) 형태로 콜백을 일으키기 위해서

import { React, useState, useRef } from 'react';
import { Box } from '@mui/material';

const FileUploadButton = ({accept, onUploadFile, children, ...props}) => {
    const fileUploadRef = useRef()
  
    const [onUploadedFileChanged] = useState(() => {
      return (e) => {
        if(!(e.target.files[0])) return;
  
        const reader = new FileReader();
        reader.addEventListener("load", () => {
            onUploadFile(e.target.files[0].name, reader.result)
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
          accept={accept}
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

export default FileUploadButton;