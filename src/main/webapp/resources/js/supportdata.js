var URLMapping = {
    "User_getCurrentUser": {url: '/user/currentUser', method: 'GET'},
    "User_getUser": {url: '/user/queryUserByEmpId', method: 'GET'},
    "User_findBucUser": {url: '/user/queryUserByKey', method: 'GET', isArray: false},
    "User_heartbeat": {url: '/user/heartbeat', method: 'GET'},
	"Search_quickSearch": {url: '/openSearch/suggestionByKeyword?keyword=:keyword', method: 'GET'},

    "ProjectDetail_buStoreStatictisDetail": {url: '/metaTable/storageDetailByApp', method: 'GET'},
    "ProjectDetail_storIncOverBaselineTables": {url: '/table/stat/storageIncOverBaseline', method: 'GET'},
    "ProjectDetail_listSpecProjDetails": {url: '/project/stat/resourceConsume', method: 'GET'},
    "ProjectDetail_metaApp": {url: '/metaApp/queryByGuid', method: 'GET'},
    "ProjectDetail_metaAppSave": {url: '/metaApp/updateBaseInfo', method: 'POST', headers: {'Content-Type': 'application/x-www-form-urlencoded'}},
    "ProjectDetail_metaAppSaveLabel": {url: '/metaApp/updateLabel', method: 'POST', headers: {'Content-Type': 'application/x-www-form-urlencoded'}},
    "ProjectDetail_metaAppStoragePredict": {url: '/project/stat/storagePredict', method: 'GET', isArray: false},
    "ProjectDetail_metaAppStatistics": {url: '/metaTable/tableCountByApp', method: 'GET'},

    "TableDetail_getAuctionIdByTableId": {url: '/metaTable/getAuctionIdByTableId', method: 'GET'},
    "TableDetail_genCreateTableSql": {url: '/metaTable/genCreateTableSql', method: 'GET'},
    "TableDetail_get": {url: '/metaTable/queryByGuid', method: 'GET'},
    "TableDetail_changeHistory": {url: '/changelog/changeHistory', method: 'GET'},
    "TableDetail_outputInfo": {url: '/metaTableOutputInfo/queryOutputInfo', method: 'GET'},
    "TableDetail_save": {url: '/metaTable/updateBaseInfo', method: 'POST', headers: {'Content-Type': 'application/x-www-form-urlencoded'}},
    "TableDetail_updateLabels": {url: '/metaTable/updateLabels', method: 'POST', headers: {'Content-Type': 'application/x-www-form-urlencoded'}},
    "Column_get": {url: '/metaColumn/queryByTableGuid', method: 'GET', isArray: false},
    "Column_save": {url: '/metaColumn/save', method: 'POST', headers: {'Content-Type': 'application/x-www-form-urlencoded'}},
    "Partition_get":{url: '/metaPartition/queryByTableGuid', method: 'GET'},
    
    "Table_listProblems": {url: '/metaTableProblem/queryByTableGuid', method: 'GET', isArray: false},
    "Table_saveNewProblem": {url: '/metaTableProblem/add', method: 'POST', headers: {'Content-Type': 'application/x-www-form-urlencoded'}},
    "Table_updateProblem": {url: '/metaTableProblem/update', method: 'POST', headers: {'Content-Type': 'application/x-www-form-urlencoded'}},
    "Table_fixProblem": {url: '/metaTableProblem/fix', method: 'POST', headers: {'Content-Type': 'application/x-www-form-urlencoded'}},
    "Table_listComments": {url: '/table/comment/list', method: 'GET', isArray: false},
    "Table_permissionInfo": {url: '/metaTable/permissionInfo', method: 'GET', isArray: false},
    "Table_saveNewComment": {url: '/table/comment/add', method: 'POST', headers: {'Content-Type': 'application/x-www-form-urlencoded'}},
    
    "TableRelation_statistic":{url: '/metaTableRelation/statistic', method: 'GET' },
    "impactAnalysis_get": {url: '/impactAnalysis/analysis2', method: 'GET'},
    
    "Search_hotKeywords": {url: '/openSearch/hotKeywords', method: 'GET'},
    "Search_listStat": {url: '/table/search/count', method: 'GET', isArray: false},
    "Search_getFilters": {url: '/store/resource/mockdata/myTable.json', method: 'GET', isArray: false},
    "Search_list":{url: '/table/search/s', method: 'GET'},

    "Table_listCommonData": {url: '/commonDataOper/getData', method: 'GET', isArray: false},
    "Table_saveCommonData": {url: '/commonDataOper/saveData', method: 'POST', headers: {'Content-Type': 'application/x-www-form-urlencoded'}},
    "Table_getAllTableNames": {url: '/commonDataOper/getAllTableNames', method: 'GET', isArray: false}
};