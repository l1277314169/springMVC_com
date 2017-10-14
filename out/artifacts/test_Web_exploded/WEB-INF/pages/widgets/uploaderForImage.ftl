<script type="text/javascript">
jQuery(function() {
    	var $ = jQuery,
        $list_${objectIdFTL} = $('#fileList_${objectIdFTL}'),
        // 优化retina, 在retina下这个值是2
        ratio_${objectIdFTL} = window.devicePixelRatio || 1,

        // 缩略图大小
        thumbnailWidth_${objectIdFTL} = 100 * ratio_${objectIdFTL},
        thumbnailHeight_${objectIdFTL} = 100 * ratio_${objectIdFTL},

        // Web Uploader实例
        uploader_${objectIdFTL};

    // 初始化Web Uploader
    uploader_${objectIdFTL} = WebUploader.create({

        // 自动上传。
        auto: true,

        // swf文件路径
        swf: 'Uploader.swf',

        // 文件接收服务端。
        server: '/uploadComm/upload',

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#filePicker_${objectIdFTL}',

        // 只允许选择文件，可选。
        accept: {
            title: 'Images',
            extensions: 'jpg,jpeg,png',
            mimeTypes: 'image/*'
        }
    });

    // 当有文件添加进来的时候
    uploader_${objectIdFTL}.on( 'fileQueued', function( file ) {
        var $li = $(
                '<div id="' + file.id + '" class="file-item thumbnail upload-state-done">' +
                    '<img>' +
                    //'<div class="info">' + file.name + '</div>' +
                    '<div class="info_del"></div>' +
                '</div>'
                ),
            $img = $li.find('img');

        $list_${objectIdFTL}.append( $li );
        
        // 创建缩略图
        uploader_${objectIdFTL}.makeThumb( file, function( error, src ) {
            if ( error ) {
                $img.replaceWith('<span>不能预览</span>');
                return;
            }

            $img.attr( 'src', src );
        }, thumbnailWidth_${objectIdFTL}, thumbnailHeight_${objectIdFTL} );
    });

    // 文件上传过程中创建进度条实时显示。
    uploader_${objectIdFTL}.on( 'uploadProgress', function( file, percentage ) {
        var $li = $( '#'+file.id ),
            $percent = $li.find('.progress span');

        // 避免重复创建
        if ( !$percent.length ) {
            $percent = $('<p class="progress"><span></span></p>')
                    .appendTo( $li )
                    .find('span');
        }

        $percent.css( 'width', percentage * 100 + '%' );
    });

    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader_${objectIdFTL}.on( 'uploadSuccess', function( file,data ) {
        if(data.code=="success"){
            //alert(data.code+","+data.message);
            $( '#'+file.id ).addClass('upload-state-done');
            $( '#'+file.id ).attr("val",data.message);

            $img = $( '#'+file.id ).find('img');
            var clientId = $("#clientId").val();
            $img.attr('href',"/uploadfiles/"+clientId+"/web/thumbnail/xl_"+data.message+"");
            
            //var str = "<a target='_black' href='/uploadfiles/"+clientId+"/web/"+data.message+"'>原图</a>";
            //if(clientId==20){
				 //$('#'+file.id).find('.info_del').after(str);            
            //}
            $(".upload-state-done img").lightBox();
        }else{
            var $li = $( '#'+file.id ),
            $error = $li.find('div.error');
            // 避免重复创建
            if ( !$error.length ) {
                $error = $('<div class="error"></div>').appendTo( $li );
            }
            $error.text('上传失败');
        }
    });

    // 文件上传失败，现实上传出错。
    uploader_${objectIdFTL}.on( 'uploadError', function( file ) {
        var $li = $( '#'+file.id ),
            $error = $li.find('div.error');

        // 避免重复创建
        if ( !$error.length ) {
            $error = $('<div class="error"></div>').appendTo( $li );
        }

        $error.text('上传失败');
    });

    // 完成上传完了，成功或者失败，先删除进度条。
    uploader_${objectIdFTL}.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress').remove();
    });
    
    //删除
    $list_${objectIdFTL}.on("click", ".info_del", function () {
        var $ele = $(this);
        var id = $ele.parent().attr("id");
        uploader_${objectIdFTL}.removeFile(uploader_${objectIdFTL}.getFile(id));
        $(this).parent(".file-item").remove();
    });
    
    
    $(".info_del").live("click",function(){
         $(this).parent(".file-item").remove();
    });
    
});
</script>