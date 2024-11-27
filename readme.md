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



