<template>
  <div class="footer">{{copyright}}</div>
</template>

<script>
export default {
  props: ['copyright']
}
</script>

<style lang="css">
.footer {
  line-height: 52px;
  font-size: 14px;
  background: #4e4e4e;
  text-align: center;
  color: #7d7d7d;
}
</style>
