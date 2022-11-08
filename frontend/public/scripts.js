function httpGet() {
  var xmlHttp = new XMLHttpRequest();
  xmlHttp.open("GET", '/api/login', false); // false for synchronous request
  xmlHttp.send(null);
  return xmlHttp.responseText;
}

console.log(xmlHttp.responseText);
