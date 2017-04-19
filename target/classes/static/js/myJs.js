var textarea1 = document.getElementById("textarea1");
var textarea2 = document.getElementById("textarea2");
var limit1 = 200;
var limit2 = 700;

textarea1.oninput = function() {
  textarea1.style.height = "";
  textarea1.style.height = Math.min(textarea1.scrollHeight, limit1) + "px";
};

textarea2.oninput = function() {
  textarea2.style.height = "";
  textarea2.style.height = Math.min(textarea2.scrollHeight, limit2) + "px";
};