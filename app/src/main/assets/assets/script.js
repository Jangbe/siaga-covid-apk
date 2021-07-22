$(document).ready(()=>{
	if($('#welcome')[0]){
		$('#welcome')[0].volume = .5
		$('#welcome')[0].muted = false
	}
})
$('.title').children().animate({
	'text-indent': 0
}, 1000)

setInterval(()=>{
	$('.menu').animate({
		// marginRight: -10,
		marginLeft: -10,
	},1000).animate({
		// marginRight: 10,
		marginLeft: 10
	},1000)
}, 2000)

$(window).on('resize', ()=>{
    checkScrollable()
})
function checkScrollable(){
    if ($("body").height() > $(window).height()) {
      $('html').css({height: 'max-content'})
    }else{
      $('html').css({height: '100%'})
    }
}
checkScrollable()

$('a, button').click((e)=>{
	App.click()
})