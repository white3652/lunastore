 // #region 체크박스 전체 선택
 //  function checkbox() {
 //      const selectAllCheckbox = document.getElementById('all');
 //      const individualCheckboxes = document.querySelectorAll('.restCheckBox');

 //      // 전체 선택 체크박스가 변경될 때 이벤트 처리
 //      selectAllCheckbox.addEventListener('change', function () {
 //          // 전체 선택 체크박스의 상태에 따라 개별 체크박스들의 상태 변경
 //          individualCheckboxes.forEach(checkbox => {
 //              checkbox.checked = selectAllCheckbox.checked;
 //          });
 //      });

 //      // 개별 체크박스 중 하나라도 변경될 때 이벤트 처리
 //      individualCheckboxes.forEach(checkbox => {
 //          checkbox.addEventListener('change', function () {
 //              // 개별 체크박스 중 하나라도 해제되면 전체 선택 체크박스도 해제
 //              if (!checkbox.checked) {
 //                  selectAllCheckbox.checked = false;
 //              } else {
 //                  // 모든 개별 체크박스가 선택되었을 때 전체 선택 체크박스도 선택
 //                  const allChecked = Array.from(individualCheckboxes).every(checkbox => checkbox.checked);
 //                  if (allChecked) {
 //                      selectAllCheckbox.checked = true;
 //                  }
 //              }
 //          });
 //      });
 //  }
 // #endregion

 // #region 카테고리 분류
 // 대분류 선택 시 실행되는 함수

 $(function () {

     cateChange();

     $('#largeCategory').on('change', function () {
         cateChange();
     })

     const smallCategorys = $("#smallCategorys").val();
     let smallCategory = $("#smallCategory").find("option");
     smallCategory.each(function () {
         if ($(this).val() == smallCategorys) {
             $(this).attr("selected", true);
         }
     });

     function cateChange() {

         var largeCategorySelect = $('#largeCategory');
         var smallCategorySelect = $('#smallCategory');

         // 대분류에 따른 중분류 매핑
         var categoryMap = {
             "1": {
                 "전체": 0,
                 "휴대폰": 1,
                 "영상가전": 2,
                 "PC": 3,
                 "음향가전": 4,
                 "생활가전": 5
             },
             "2": {
                 "전체": 0,
                 "수납": 6,
                 "홈": 7,
                 "거실": 8,
                 "침구": 9,
                 "침실가구": 10
             },
             "3": {
                 "전체": 0,
                 "농수산물": 11,
                 "가공식품": 12,
                 "제과제빵": 13,
                 "음료": 14,
                 "건강식품": 15
             },
             "4": {
                 "전체": 0,
                 "상의": 16,
                 "하의": 17,
                 "아우터": 18,
                 "신발": 19,
                 "이너웨어": 20
             },
             "5": {
                 "전체": 0,
                 "취미": 21,
                 "잡화": 22,
                 "주방": 23,
                 "사무": 24,
                 "공구": 25
             }
         };

         var selectedCategory = largeCategorySelect.val();
         var f = categoryMap[selectedCategory] || {
             "전체": 0
         };

         smallCategorySelect.empty();


         $.each(f, function (categoryName, categoryId) {
             var opt = $('<option>', {
                 value: categoryId,
                 html: categoryName
             });

             smallCategorySelect.append(opt);
         });
     }
   
 });

 // #endregion