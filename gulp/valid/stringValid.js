function isEmpty(str) {
    if (str == null || str.trim() == '') {
        return true;
    }
    return false;
}

function isNotEmpty(str) {
    return !isEmpty(str);
}

module.exports = {
    isEmpty: isEmpty,
    isNotEmpty: isNotEmpty
}