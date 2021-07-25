// Copyright (c) 2019 ml5
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

/* ===
ml5 Example
Webcam Image Classification using a pre-trained customized model and p5.js
This example uses p5 preload function to create the classifier
=== */

// Classifier Variable
let classifier;
// Model URL
// let imageModelURL = 'https://teachablemachine.withgoogle.com/models/BlP1OZ89F/model.json';
let ip = "http://0.0.0.0:8765";
let imageModelURL = ip+'/views/model/model.json';

let img;

let label = "";

var pakaiMasker
document.addEventListener('DOMContentLoaded',()=>{
  pakaiMasker = document.querySelector("#pakai-masker")
})

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
  loadImage("contoh.jpg", imageReady);
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
  if(label!="Correct: Mask On"){
    pakaiMasker.play()
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