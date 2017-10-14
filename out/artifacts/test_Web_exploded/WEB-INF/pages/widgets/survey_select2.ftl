<script type="text/javascript">
	$("#${surveyFtlName}").select2({
        	width:150,
			placeholder: "输入字符查询",
			minimumInputLength: 2,
			allowClear: true,
			ajax: {
				url: "${contextPath}/survey/getSurveyByName",
				dataType: 'json',
				quietMillis: 250,
				data: function (term, page) {
					return {
						q: term,
						page: page
					};
				},
				results: function (data,page) {
					return { results: data};
				}
			},
			initSelection: function(element, callback) {
				var id = $(element).val();
				if (id !== "") {
					$.ajax("${contextPath}/survey/getSurvey/"+id, {
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
</script>