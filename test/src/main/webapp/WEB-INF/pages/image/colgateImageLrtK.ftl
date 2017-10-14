<style type="text/css">
.list_${name[0]} div {
	text-align: center;
	float: left;
}

.btnPrev_${name[0]}, .btnNext_${name[0]}, .listBox_${name[0]} {
	float: left;
}

.btnPrev_${name[0]}, .btnNext_${name[0]} {
	width: 30px;
	height: 18px;
	line-height: 18px;
	padding: 41px 0;
	text-align: center;
}

.listBox_${name[0]} {
	width: 820px;
	height: 150px;
	overflow: hidden;
}

.container_${name[0]} {
	width: 900px;
	height: 150px;
}
</style>

<div style="height:50px;line-height:50px;margin:0 auto;">${name[1]}-${name[2]}</div>
	
	<div class="container_${name[0]}">
        <div class="btnNext_${name[0]}" id="btnNext_${name[0]}" title="上一个">
            <<
        </div>
        <div class="listBox_${name[0]}" id="listBox_${name[0]}">
            
            <div class="list_${name[0]}" id="list_${name[0]}">
	            <#list images[key] as ig>
					<div style="height:150px;width:120px;float: left;margin:8px;" class="child_list">
						<a href="${contextPath}/uploadfiles/${ig.largeImagepath}" rel="flowers" class="lightPhoto_${name[0]}" style="display: block;float: left;">
							<img src="${contextPath}/uploadfiles/${ig.smallImagePath}" style="width:120;height:120;" />
						</a>
						<span style="float: left;height: 30px;width: 120px;text-align: center;line-height: 30px;vertical-align: middle;">
							${ig.imageShowName}
						</span>
					</div>
				</#list>
			</div>
        </div>
        <div class="btnPrev_${name[0]}" id="btnPrev_${name[0]}" title="下一个">
            >>
        </div>
    </div>

<script type="text/javascript">
	jQuery(document).ready(function() {
		
		$("#listBox_${name[0]}").imageScroller({
            next: "btnNext_${name[0]}",
            prev: "btnPrev_${name[0]}",
            frame: "list_${name[0]}",
            child: "div",
            auto: false
        });

		$(".lightPhoto_${name[0]}").lightbox({
			fitToScreen: true
		});
	});
</script>