Comment l'utiliser 

- Pour sauver les numeros qui sont eté vendus:
acess {numbers/post}

- Pour obtenir le billet qui vient d'être vendu c'est
@Get {numbers/download/<ID>}
L'id est retourné quand l'object est sauvaguardé et 
on prends ça pour envoyer sur le lien

- Pour manipuler les numeros choisis comme resultat:
Envoyer comme par @Post {chosen/post} avec le contenue comme

  {
    "chosenNumbers": [x,x,x,x,x,x,x,x,x,x,x,]
  }

- Pour comparer les numeros resultat avec les numeros
qui sont éte vendus: @Post {chosen/compare} et le corps du
message c'est le meme que le dernier

- Pour obtenir tout les numeros vendus dans un pdf c'est
avec un @Get {chosen/download/<listNumeros>}


Como usar
- Para salvar os números que foram vendidos: acesse {numbers/post}
- Para obter o bilhete que acabou de ser vendido: @Get {numbers/download/<ID>}. O ID é retornado quando o objeto é salvo e usamos ele para acessar o link.
- Para manipular os números escolhidos como resultado: enviar via @Post {chosen/post} com o conteúdo
{"chosenNumbers": [x, x, x, x, x, x, x, x, x, x, x]}
- Para comparar os números do resultado com os números que foram vendidos: @Post {chosen/compare} O corpo da mensagem é o mesmo do anterior.
-Para obter todos os números vendidos em PDF: @Get {chosen/download/<listNumeros>}


