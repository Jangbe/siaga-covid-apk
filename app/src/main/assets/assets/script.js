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
		marginLeft: -10,
	},1000).animate({
		marginLeft: 10
	},1000)
}, 2000)

$(window).on('resize', ()=>{
    checkScrollable()
})
function checkScrollable(){
	$(document).ready(()=>{
		if ($("body").height() > $(window).height()) {
		  $('html').css({height: 'max-content'})
		}else{
		  $('html').css({height: '100%'})
		}
	})
}
checkScrollable()

$('a, button').click((e)=>{
	if(typeof App != undefined){
		App.click()
	}
})

$('.subtitle').css({
	transform: 'scale(1)'
})
$('.deskripsi').animate({
	opacity: 1
},1000);


function makeDiv(){
    var divsize = ((Math.random()*10) + 30).toFixed();
    $newdiv = $('<img src="../assets/icon/coronavirus.png"/>').css({
        'width':divsize+'px',
        'height':divsize+'px',
		'z-index':-99
    });
    
    var posx = (Math.random() * ($(document).width() - divsize)).toFixed();
    var posy = (Math.random() * ($(document).height() - divsize)).toFixed();
    
    $newdiv.css({
        'position':'absolute',
        'left':posx+'px',
        'top':posy+'px',
        'display':'none'
    }).appendTo( 'body' ).fadeIn(100).delay(300).fadeOut(200, function(){
       $(this).remove();
    }); 
}
setInterval(()=>{
	for(let i = 0; i<=4;i++){
		makeDiv();
	}
},1000)
