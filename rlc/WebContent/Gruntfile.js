module.exports = function(grunt) {
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    sass: {
      dist: {
        files: {
          'stylesheets/main.css' : 'stylesheets/sass/main.scss'
        }
      }
    },
    watch: {
      css: {
        files: ['stylesheets/sass/*.scss', 'stylesheets/sass/settings/*.scss'],
        tasks: ['sass']
      }
    }
  });
  grunt.loadNpmTasks('grunt-contrib-sass');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.registerTask('default',['watch']);
}