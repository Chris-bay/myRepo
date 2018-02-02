
function loadData() {
  $.getJSON("https://api.ethermine.org/poolStats", function(data){
    console.log(data.data.price.usd);
    document.getElementById("prize").innerHTML = "Preis in USD: " + data.data.price.usd;
  });
  $.getJSON("https://api.ethermine.org/miner/cA29A952103a59e0d62417307742A23e7476AB8e/rounds", function(data){
    console.log(data.data[2].block);
    document.getElementById("block").innerHTML = "Block: " + data.data[2].block;
  });
}