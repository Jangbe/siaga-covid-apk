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