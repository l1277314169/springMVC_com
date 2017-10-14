<style type="text/css">
.list_${img.showDate} div {
	text-align: center;
	float: left;
}

.btnPrev_${img.showDate}, .btnNext_${img.showDate}, .listBox_${img.showDate} {
	float: left;
}

.btnPrev_${img.showDate}, .btnNext_${img.showDate} {
	width: 30px;
	height: 18px;
	line-height: 18px;
	padding: 41px 0;
	text-align: center;
}

.listBox_${img.showDate} {
	width: 820px;
	height: 150px;
	overflow: hidden;
}

.container_${img.showDate} {
	width: 900px;
	height: 150px;
}
</style>

<div style="height:50px;line-height:50px;margin:0 auto;">${img.showDate}（共${img.size}张）</div>
	
	<div class="container_${img.showDate}">
        <div class="btnNext_${img.showDate}" id="btnNext_${img.showDate}" title="上一个">
            <<
        </div>
        <div class="listBox_${img.showDate}" id="listBox_${img.showDate}">
            
            <div class="list_${img.showDate}" id="list_${img.showDate}">
	            <#list img.images as ig>
					<div style="height:150px;width:120px;float: left;margin:8px;" class="child_list">
						<a href="${contextPath}/uploadfiles/${ig.largeImagepath}" rel="flowers" class="lightPhoto_${img.showDate}" style="display: block;float: left;">
							<img src="${contextPath}/uploadfiles/${ig.smallImagePath}" style="width:120;height:120;" />
						</a>
						<span style="float: left;height: 30px;width: 120px;text-align: center;line-height: 30px;vertical-align: middle;">
							${ig.imageShowName}
						</span>
					</div>
				</#list>
			</div>
        </div>
        <div class="btnPrev_${img.showDate}" id="btnPrev_${img.showDate}" title="下一个">
            >>
        </div>
    </div>

<script type="text/javascript">
	jQuery(document).ready(function() {
		
		$("#listBox_${img.showDate}").imageScroller({
            next: "btnNext_${img.showDate}",
            prev: "btnPrev_${img.showDate}",
            frame: "list_${img.showDate}",
            child: "div",
            auto: false
        });

		$(".lightPhoto_${img.showDate}").lightbox({
			fitToScreen: true
		});
	});
</script>