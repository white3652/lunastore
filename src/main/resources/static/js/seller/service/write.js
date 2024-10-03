$(function () {

    var contextPath = document.getElementById('dataContainer').dataset.contextPath;

    function isImageFile(file) {
        var ext = file.name.split(".").pop().toLowerCase();
        if ($.inArray(ext, ["jpg", "jpeg", "gif", "png"]) === -1) {
            alert("이미지 파일만 등록할 수 있습니다.")
            return false;
        } else {
            return true;
        }
    }

    function isOverSize(file) {
        var maxSize = 3 * 1024 * 1024; // 3MB로 제한
        if (file.size > maxSize) {
            alert("3MB 이하의 파일만 등록할 수 있습니다.")
            return false;
        } else {
            return true;
        }
    }

    var counter = $('.imgfile').length; // 이미 생성된 file 태그의 개수

    // 추가 버튼 클릭 시 이벤트 처리
    $("#btn-addItemImg").on("click", function () {
        if (counter < 5) {
            var newInputId = "imageFile" + counter;
            var newPreviewClass = "preview";

            // 새로운 파일 입력란 생성
            var newInput = $('<input type="file" id="' + newInputId + '" name="i_img' +
                counter + '" class="imgfile">');
            $(".wrap section form").append(newInput);

            // 새로운 레이블과 미리보기 이미지 생성
            var newLabel = $('<label for="' + newInputId + '"><img class="' + newPreviewClass +
                '"></label>');
            $(".wrap section .itemimgbox").append(newLabel);

            // 새로운 버튼과 미리보기 이미지 생성
            var newButton = $('<button class="' + newInputId + '"><img class="' +
                newPreviewClass + '"></button>');
            $(".buttonImg").append(newButton);

            // 파일 입력란의 change 이벤트 처리
            $("#" + newInputId).on("change", function (event) {
                var file = event.target.files[0];
                if (isImageFile(file) && isOverSize(file)) {
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        $('label[for="' + newInputId + '"]').find('.preview').attr(
                            "src", e.target.result);
                        $('button[class="' + newInputId + '"]').find('.preview')
                            .attr("src", e.target.result);
                    }
                    reader.readAsDataURL(file);
                }
            });
            $(".itemimgbox").css("width", 600 * (counter + 1) + "px");
            $('button[class="' + newInputId + '"]').trigger('click');
            counter++; // 카운터 증가
        } else {
            alert("상품 이미지는 최대 5개까지 등록할 수 있습니다.")
        }
    });

    $("#btn-removeItemImg").on("click", function () {
        if (counter > 0) {
            var lastInputId = "imageFile" + (counter - 1);
            $("#" + lastInputId).remove();
            $('label[for="' + lastInputId + '"]').remove();
            $('button[class="' + lastInputId + '"]').remove();
            $(".itemimgbox").css("width", 600 * (counter - 1) + "px");
            $('button[class="imageFile' + (counter - 2) + '"]').trigger('click');
            counter--;
        } else {
            alert("삭제할 이미지가 없습니다.");
        }
    });

    $(".buttonImg").on("click", "button", function () {
        var buttonClass = $(this).attr("class"); // 클릭한 버튼의 클래스 이름 가져오기
        var associatedInput = $('label[for="' + buttonClass + '"]'); // label 태그 찾기
        $(".buttonImg button").css({
            'border': '2px solid #F2F2F2'
        });
        $(this).css({
            'border': '2px solid black'
        });
        $("label").each(function () {
            // label 안에 img 태그가 있는 경우에만 숨김
            if ($(this).find('img').length > 0) {
                $(this).hide();
            }
        });
        associatedInput.show();
    });

    $('#largeCategory').change(function () {
        var largeCategorySelect = $('#largeCategory');
        var smallCategorySelect = $('#smallCategory');
        var hiddenInput = $('#categoryidx');
        hiddenInput.val('0');
        var a = ['휴대폰', '영상가전', 'PC', '음향가전', '생활가전'];
        var b = ['수납', '홈', '거실', '침구', '침실가구'];
        var c = ['농수산물', '가공식품', '제과제빵', '음료', '건강식품'];
        var d = ['상의', '하의', '아우터', '신발', '이너웨어'];
        var e = ['취미', '잡화', '주방', '사무', '공구'];

        var f;
        switch (largeCategorySelect.val()) {
            case "a":
                f = a;
                break;
            case "b":
                f = b;
                break;
            case "c":
                f = c;
                break;
            case "d":
                f = d;
                break;
            case "e":
                f = e;
                break;
            default:
                f = [];
        }

        smallCategorySelect.empty();

        // "분류를 선택하세요" 옵션 추가
        var defaultOption = $("<option value='0' selected disabled hidden>===== 선택 =====</option>");
        smallCategorySelect.append(defaultOption);

        $.each(f, function (index, value) {
            var optValue;
            if (f === a) {
                optValue = index + 1;
            } else if (f === b) {
                optValue = index + 6;
            } else if (f === c) {
                optValue = index + 11;
            } else if (f === d) {
                optValue = index + 16;
            } else if (f === e) {
                optValue = index + 21;
            }
            var opt = $("<option></option>").attr("value", optValue).text(value);
            smallCategorySelect.append(opt);
        });

        smallCategorySelect.change(function () {
            var selectedValue = $(this).val();
            hiddenInput.val(selectedValue); // hidden input의 값 설정
        });
    });

    var selectCount = 0; // 현재 생성된 itemoption의 개수
    var parentOptionCounts = [];

// btn-addSelect 버튼 클릭 시 itemoption 추가
    $("#btn-addSelect").click(function () {
        if (selectCount < 5) {
            if (!parentOptionCounts[selectCount]) {
                parentOptionCounts[selectCount] = 2; // 해당 부모 요소의 optionCount가 없으면 초기화
            }

            var newOption = "<div class='itemoption" + selectCount + "'>" +
                "<h4></h4>" + // 옵션 이름을 보여줄 h4 태그 (나중에 업데이트 가능)
                "<input type='text' class='itemoptionname' id='itemoptionname" + selectCount + "' >" +
                "</div>";

            var newButtons = "<div class='itemoption itemoption" + selectCount + "'>" +
                "<div class='itemoptioninfo'>" +
                "<div class='input-option'>" +
                "<input type='text' class='itemoptionname' id='itemoptionname" + selectCount + "' >" +
                "<span>옵션 " + selectCount + "</span>" +
                "</div>" +
                "<div class='input-option'>" +
                "<input type='text' class='thisitmeoption0'>" +
                "<span>세부옵션 0</span>" +
                "</div>" +
                "<div class='input-option'>" +
                "<input type='text' class='thisitmeoption1'>" +
                "<span>세부옵션 1</span>" +
                "</div>" +
                "</div>" +
                "<input type='button' class='btn-removeOption remove' value='-'>" +
                "<input type='button' class='btn-addOption add' value='+'>" +
                "</div>";

            var newInput = $("<input type='hidden' id='itemoption" + selectCount + "' name='i_option" + selectCount + "'>");
            $(".wrap section form").append(newInput);
            $('.hiddenoptions').append(newOption);
            $(".wrap section .itemoptionsinput").append(newButtons);
            $(".itemoption" + selectCount).hide().fadeIn();
            selectCount++;
        } else {
            alert("옵션은 최대 5개까지 추가할 수 있습니다.");
        }
    });

// 폼 전송 시 옵션 값을 실제 값으로 설정
    $('form').submit(function (event) {
        $('.hiddenoptions div').each(function (index) {
            var optionName = $('#itemoptionname' + index).val();
            var optionValue0 = $('.itemoption' + index + ' .thisitmeoption0').val();
            var optionValue1 = $('.itemoption' + index + ' .thisitmeoption1').val();

            // 숨겨진 input에 값 설정
            $('#itemoption' + index).val(optionName + '|' + optionValue0 + '|' + optionValue1);
        });
    });

    $("#btn-removeSelect").click(function () {
        if (selectCount > 0) {
            var lastOptionClass = "itemoption" + (selectCount - 1);

            // 클래스가 존재할 경우에만 애니메이션 및 제거 수행
            if ($("." + lastOptionClass).length) {
                $("." + lastOptionClass).animate({
                    opacity: 0
                }, 150, function() {
                    $(this).remove();
                });
            }

            // ID가 존재할 경우에만 제거 수행
            if ($("#" + lastOptionClass).length) {
                $("#" + lastOptionClass).fadeOut().remove();
            }

            // parentOptionCounts 배열이 정의되어 있을 경우에만 값을 수정
            if (typeof parentOptionCounts[(selectCount - 1)] !== 'undefined') {
                parentOptionCounts[(selectCount - 1)] = 2;
            }

            // selectCount 감소
            selectCount--;
        } else {
            alert("삭제할 옵션이 없습니다.");
        }
    });

    $(document).on("click", ".btn-addOption", function () {
        var parent = $(this).parent(); // 각 버튼의 부모 요소를 찾음
        var parentIndex = $('.itemoption').index(parent);
        var parentClass = 'itemoption' + parentIndex;
        // 해당 클래스명과 같은 클래스를 가지고 있는 hiddenoptions 내의 셀렉트 박스에 옵션 추가
        if (parentOptionCounts[parentIndex] < 5) {
            $(".hiddenoptions ." + parentClass).append(
                "<input type='radio' class='thisitmeoption" +
                parentOptionCounts[parentIndex] + "' id='thisradio"+ parentIndex +
                parentOptionCounts[parentIndex] + "' name='" + parentClass + "'>" +
                "<label for='thisradio"+ parentIndex + parentOptionCounts[parentIndex] +
                "'><span></span></label>"
            );
            parent.children(".itemoptioninfo").append(
                "<div class='input-option'>" +
                "<input type='text' class='thisitmeoption" + parentOptionCounts[parentIndex] + "' placeholder=' '>" +
                "<span>세부옵션"+ parentOptionCounts[parentIndex] +"</span>" +
                "</div>");
            parentOptionCounts[parentIndex]++;
        } else {
            alert("세부옵션은 최대 5개까지 추가할 수 있습니다.")
        }
    });

    $(document).on("click", ".btn-removeOption", function () {
        var parent = $(this).parent(); // 각 버튼의 부모 요소를 찾음
        var parentIndex = $('.itemoption').index(parent);
        var parentClass = 'itemoption' + parentIndex;
        if (parentOptionCounts[parentIndex] > 2) {
            parentOptionCounts[parentIndex]--; // 해당 부모 요소의 optionCount를 감소
            // 해당 클래스명을 가진 hiddenoptions 내의 셀렉트 박스에서 마지막 옵션을 제거
            $(".hiddenoptions ." + parentClass + " input[type='radio'].thisitmeoption" +
                parentOptionCounts[parentIndex]).remove().each(function () {
                var radioId = $(this).attr('id');
                $(".hiddenoptions ." + parentClass + " label[for='" + radioId + "']")
                    .remove();
            });
            // 해당 클래스명을 가진 itemoptioninfo 내의 텍스트 입력 필드를 제거
            parent.children(".itemoptioninfo").children(".input-option").last().remove();
        } else {
            alert("최소 2개의 세부옵션이 필요합니다.")
        }
    });

    $(document).on("input", ".itemoptionname", function () {
        var inputValue = $(this).val(); // 입력받은 값
        // 부모의 부모 div 요소의 클래스 리스트
        var parentDivClasses = $(this).parent().parent().parent().attr('class').split(' ');
        // itemoption 클래스를 제외한 클래스명 가져오기
        var filteredClasses = parentDivClasses.filter(function (className) {
            return className !== 'itemoption';
        });
        var parentDivClass = filteredClasses.join(' '); // 필터링된 클래스명
        // 클래스명이 같은 div 내의 h4 태그에 입력받은 값 삽입
        $("div." + parentDivClass + " h4").text(inputValue);

    });

    $(document).on("input", "input:not(.itemoptionname)", function () {
        var inputValue = $(this).val(); // 입력받은 값
        var parentDivClasses = $(this).parent().parent().parent().attr('class').split(' ');
        // 부모의 부모 div 요소의 클래스 리스트

        // itemoption 클래스를 제외한 클래스명 가져오기
        var filteredClasses = parentDivClasses.filter(function (className) {
            return className !== 'itemoption';
        });
        var parentDivClass = filteredClasses.join(' '); // 필터링된 클래스명

        // 부모의 부모 div와 같은 클래스명을 가진 div 내에서 현재 input 요소와 동일한 클래스명을 가진 option 태그를 찾아서 값 설정
        var option = $("div." + parentDivClass + " input[type='radio']." + $(this).attr('class'));
        option.val(inputValue); // option 태그의 값 변경
        option.each(function () {
            var radioId = $(this).attr('id');
            console.log(radioId);
            $("div." + parentDivClass + " label[for='" + radioId + "']").text(inputValue);
        });
    });

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // HTML에서 imageUploadUrl과 imagePrintUrl을 받아옵니다.
    var imageUploadUrl = document.getElementById('dataContainer').dataset.imageUploadUrl;
    var imagePrintUrl = document.getElementById('dataContainer').dataset.imagePrintUrl;

    const editor = new toastui.Editor({
        el: document.querySelector('#editer_content'), // 에디터를 적용할 요소 (컨테이너)
        height: '1000px', // 에디터 영역의 높이 값 (OOOpx || auto)
        initialEditType: 'wysiwyg', // 최초로 보여줄 에디터 타입 (markdown || wysiwyg)
        initialValue: '', // 내용의 초기 값으로, 반드시 마크다운 문자열 형태여야 함
        previewStyle: 'tab', // 마크다운 프리뷰 스타일 (tab || vertical)
        placeholder: '내용을 입력해 주세요.',
        /* start of hooks */
        hooks: {
            async addImageBlobHook(blob, callback) { // 이미지 업로드 로직 커스텀
                try {

                    const formData = new FormData();
                    formData.append('image', blob);

                    // 이미지 업로드 URL로 POST 요청
                    const response = await fetch(imageUploadUrl, {
                        method: 'POST',
                        body: formData,
                    });

                    // 컨트롤러에서 전달받은 디스크에 저장된 파일명
                    const filename = await response.text();

                    // 에디터에 이미지를 렌더링
                    const imageUrl = imagePrintUrl + '?filename=' + filename;
                    callback(imageUrl, 'image alt attribute');

                } catch (error) {
                    console.error('업로드 실패 : ', error);
                }
            }
        }
        /* end of hooks */
    });

    $('form').submit(function (event) {
        var editerHTML = editor.getHTML();
        $('.hiddenoptions div').each(function () {
            var divId = $(this).attr('class');
            $('#' + divId).val($(this).html());
        });
        document.getElementById('itemexplain').value = editerHTML;
    });
});
