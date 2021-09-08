<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<script>

//문서들이 다 읽혀지고 얘가 읽혀야되기에 window.onload
function Summernote_start(targetObj){
   targetObj.summernote({ //다 onload된 상태이기 떄문에 jQuery가능
      placeholder:'여기에 내용을 적으세요.',
      lang:'ko-KR',
      height:250,
      disableResizeEditor:true, //창크기 사용자가 변경할 수있는데 그걸 못하게 하려고!
      callbacks:{
         onImageUpload : function(files, editor, welEditable){
            //file size check!
            for (var file of files){
               if(file.size > 1024 * 1024 * 5){
                  alert("이미지는 5MB 미만입니다.");
                  return;  //파일이미지가 5mb이상이면 리턴시킴 function에 대한 리턴
               }
               if(file.name.substring(file.name.lastIndexOf(".")+1).toUpperCase() != "JPG"){
                  alert("JPG 이미지형식만 가능합니다.");
                  return;
               }
            }
               for (var file of files){ // 보내야되는데 Aajx던지려고!
                  sendFile(file,this); // summernote를 보내줘야해서 this 다?
            }
         },
         onMediaDelete : function(target){
         	//alert('delete!!');	
         		deleteFile(target[0].src);
         }
      }
   }); 
}
function sendFile(file, el){
   var form_data = new FormData();
   form_data.append("file",file);
   $.ajax({
      data: form_data,
      type: "POST",
      url: '<%=request.getContextPath()%>/uploadImg.do',
      cache: false,
      contentType: false,
      processData: false,
      success: function(img_url){
         $(el).summernote('editor.insertImage', img_url);
      },
      error:function(){
         alert("이미지 업로드에 실패했습니다.");
      }
   });
}

function deleteFile(src) {
	   //src=src.replace("getImg.do","deleteImg.do"); 다른 방법 자르는
	  
	   var splitSrc = src.split("=");
	   var fileName = splitSrc[splitSrc.length-1];
	   var fileData = {fileName:fileName};
	   
	   $.ajax({
		 url:"<%=request.getContextPath()%>/deleteImg.do",
		 data : JSON.stringify(fileData),
		 type:"post",
		 contentType:"application/json",
		 success:function(res){
			 console.log(res);
		 },
		 error:function(){
			 alert("이미지 삭제가 불가합니다.");
		 }
	   });
}



</script>