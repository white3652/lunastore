		$(function() {
			changeCtgr();

			$('#admin_ctgr1').on('change', function() {
				changeCtgr();
			});

			const selectedLargeCategory = $('#admin_ctgr1').val();
			const smallCategories = $("#admin_ctgr2").val();
			$('#admin_ctgr2').val(smallCategories).change();

			function changeCtgr() {
				var largeCategorySelect = $('#admin_ctgr1');
				var smallCategorySelect = $('#admin_ctgr2');

				var categoryMap = {
					"1" : {
						"전체" : 0,
						"휴대폰" : 1,
						"영상가전" : 2,
						"PC" : 3,
						"음향가전" : 4,
						"생활가전" : 5
					},
					"2" : {
						"전체" : 0,
						"수납" : 6,
						"홈" : 7,
						"거실" : 8,
						"침구" : 9,
						"침실가구" : 10
					},
					"3" : {
						"전체" : 0,
						"농수산물" : 11,
						"가공식품" : 12,
						"제과제빵" : 13,
						"음료" : 14,
						"건강식품" : 15
					},
					"4" : {
						"전체" : 0,
						"상의" : 16,
						"하의" : 17,
						"아우터" : 18,
						"신발" : 19,
						"이너웨어" : 20
					},
					"5" : {
						"전체" : 0,
						"취미" : 21,
						"잡화" : 22,
						"주방" : 23,
						"사무" : 24,
						"공구" : 25
					}
				};

				var selectedCategory = largeCategorySelect.val();
				var f = categoryMap[selectedCategory] || {
					"=카테고리선택=" : 0
				};

				smallCategorySelect.empty();

				$.each(f, function(categoryName, categoryId) {
					var opt = $('<option>', {
						value : categoryId,
						html : categoryName
					});

					smallCategorySelect.append(opt);
				});
			}
		});