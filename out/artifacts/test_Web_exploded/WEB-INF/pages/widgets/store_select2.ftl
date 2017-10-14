<script type="text/javascript">
	jQuery(document).ready(function() {
			$("#${storeFtlName}").select2({
			width:145,
			placeholder: "输入字符查询",
			minimumInputLength: 2,
			allowClear : true,
			ajax: {
				url: "${contextPath}/store/getStoreByName",
				dataType: 'json',
				quietMillis: 250,
				data: function (term, page) {
					return {
						q: term,
						page: page
					};
				},
				results: function (data,page) {
					var more = page;
					return { results: data,more: more };
				}
			},
			initSelection: function(element, callback) {
				var id = $(element).val();
				if (id != "") {
					$.ajax("${contextPath}/store/getStore/"+id, {
						dataType: "json"
					}).done(function(data) { callback(data); });
				}
			},
			formatResult: repoFormatResult,
			formatSelection: repoFormatSelection,
			escapeMarkup: function (m) { return m; }
		});
			
		function repoFormatResult(repo) {
			return repo.name;
		}
		function repoFormatSelection(repo) {
			return repo.name;
		}
	});
</script>