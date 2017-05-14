# 

conserver les Interface Admin : metric, documentaion swagger

docker compose : aps + database mysl + registry qui monitor tout

registery : service discovery, spring conifg, monitoring apps, metrics

# 
fron : 
Test unitaire avec karma + jasmine , lancés sous phantomJS
Test endt-to-end (facultatifs) lancé via protractor

	workflow : 
		TU : yarn run test
		TU + livereleod : yarn run test
		Test end-to-end : yarn run e2e
		
# package prod
minification du JS, CSS
Hash , dans les fichier
Tree shaking, : seulement ce qui est nécessaire est packagé
Code spliting : plusieurs bundles

contenu généré dans : target/wwww 

# Angular CLI
préconfiguré pour géénrer : 
	composants, services, routes
	
# avantages
typage
support ES2015+
un seul gestionnaire de dépendance - yarn
tree shaking
lazy loading
	