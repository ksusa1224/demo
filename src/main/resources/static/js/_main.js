﻿//QA内でのパーツの順番
var id = 2;

function qa_focus(obj)
{
	if (id == 2)
	{
		enter();
	}
}

// HTMLタグごとコピーペーストされるのを防ぐ（文字列のみにする）
$(document).on('paste', function(e){
	$('#qa_input').children('span').each(function () {
		// HTMLタグを取り除く
	    $(this).html($(this).html().replace(/<\/?[^>]+(>|$)/g,""));
		var pastedData = e.originalEvent.clipboardData.getData('text');
	    $("#qa_input span:nth-last-child(2)").html(pastedData);
	    focus_last();
	    event.preventDefault();
	});
});

// 漢字変換後にEnterを押したときにペンの色が変わる
function enter (){
	var q_parts = "<span class='q_input' id='" + id + "'>&#8203;</span>";
	var a_parts = "<span class='a_input' id='" + id + "'>&#8203;</span>";
	var last = $("#qa_input span:last").attr('class');
	var last_a = $("#qa_input span:nth-last-child(2)").text();
	if (id == 2)
	{	
		$("#qa_input").append("<span class='q_input' id='1'>&#8203;</span>");	
		$("#qa_input").append(a_parts);	
		focus_last();
		id++;
	}	
	if (window.event.keyCode == 13)
	{
		if (last == "q_input")
		{
			$("#qa_input").append(a_parts);	
			focus_last();
			id++;
						
			jQuery.ajax({
				url: "../serif.html?a=" + last_a,
				dataType: "html",
				cache: false,
				success: function(data)
				{					
					if (last_a.trim() != '\u200B')
					{
						$("#balloon").css("opacity","1");
						$("#serif").text(data);
					}
				},
				error: function(data)
				{
					alert("ajax error");
				}
			});
			
		}
		else if (last == "a_input")
		{
			$("#qa_input").append(q_parts);						
			focus_last();
			id++;
			$("#balloon").css("opacity","0");
			$("#serif").text("");
		}		
		event.preventDefault();
	}
}

// 最後の要素にカーソルを移動する
function focus_last(){
	var node = document.querySelector("#qa_input");
	node.focus();
	var textNode = null;
	textNode = node.lastChild;
	var caret = 0; // insert caret after the 10th character say
	var range = document.createRange();
	range.setStart(textNode, caret);
	range.setEnd(textNode, caret);
	var sel = window.getSelection();
	sel.removeAllRanges();
	sel.addRange(range);
}

// contenteditableはそのままformでsubmitできないためいったん非表示のテキストエリアにコピー
function copy_to_hidden () {
	// 空白タグを取り除く
	$('#qa_input').children('span').each(function () {
		// ゼロ幅のスペース
		if ($(this).html().trim() == "\u200B")
	    {
	    	$(this).remove();
	    }
	});
	var content = $("#qa_input").html();
	$("#qa_input_hidden").html(content);
    return true;
}

// 問題登録押下時、リロードせずにAjaxで登録と再検索を行う
function ajax_reload ()
{
	var qa_input = $("#qa_input_hidden").html();
	var decoded = $("#qa_input_hidden").html(qa_input).text();
	var yomudake_flg = $("#yomudake_flg").val();
	var reversible_flg = $("#reversible_flg").val();
	//alert(decoded);
	jQuery.ajax({
		url: "../register_qa.html?qa_input=" + decoded + 
				"&yomudake_flg=" + yomudake_flg +
				"&reversible_flg=" + reversible_flg,
		dataType: "html",
		cache: false,
		success: function(data)
		{				
			$("#qa_area").html(data);
			$("#qa_input").html("");
			$("#qa_input").focus();
			id = 2;
		},
		error: function(data)
		{
			alert("ajax error");
		}
	});	
}

// TODO 常にoffになってしまう
function change_val(chk_box)
{
	if($("this").val() == "off")
	{
		$("this").val() == "on"
	}
	else
	{
		$("this").val() == "off";
	}
}
