let classifier;
// Model URL
let ip = "http://0.0.0.0:8765";
let imageModelURL = ip+'/game/model/model.json';

let img;

let label = "";

function initUrl(url){
  ip = url
  console.log(imageModelURL);
  classifier = ml5.imageClassifier(imageModelURL);
}

function preload() {
  classifier = ml5.imageClassifier(imageModelURL);
}

function setup() {
  createCanvas(400, 400);
  background(0);
  loadImage("../views/contoh.jpg", imageReady);
  document.querySelector("#defaultCanvas0").style.display = "none"
}


function imageReady(img) {
  img.width = 400;
  img.height = 400;
  image(img, 0, 0);
  classifier.classify(img, gotResult);
}

let textResult;
// When we get a result
function gotResult(error, results) {
  // If there is an error
  if (error) {
    // console.error(error);
    return;
  }

  label = results[0].label;
  console.log("txt: "+label);
  textResult = label
  if(c2_callFunction){
    let resultCall = c2_callFunction("kurangiDarah")
    if(label!="Correct: Mask On"&&resultCall==1){
      $('.alert').css({bottom: 5+'%'});
      setTimeout(()=>{
          $('.alert').css({bottom: -1000});
      },4000)
    }
  }
  loadImage(img, imageReady);
}

function imgChange(base64) {
  var c=document.getElementById("defaultCanvas0");
  var ctx=c.getContext("2d");
  var newImg=new Image();
  newImg.onload = function(){
    ctx.drawImage(newImg,0,0);
  };
  newImg.src=base64;
  classifier.classify(newImg, gotResult);
  return textResult;
}