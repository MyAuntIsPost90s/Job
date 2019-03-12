function isEmpty(list) {
    if(list==null||list.length<1){
        return true;
    }
    return false;
}

function isNotEmpty(list) {
    return !isEmpty(list);
}

function isNull(obj) {
    return obj==null;
}

function isNotNull(obj) {
    return !isNull(obj);
}

module.exports={
    isEmpty:isEmpty,
    isNotEmpty:isNotEmpty,
    isNotNull:isNotNull,
    isNull:isNull
}