
var cke_2_contents = document.getElementById("cke_2_contents");
var limit1 = 200;
var limit2 = 700;


cke_2_contents.oninput = function() {
  cke_2_contents.style.height = "";
  cke_2_contents.style.height = Math.min(cke_2_contents.scrollHeight, limit2) + "px";
};s