module.exports = function(grunt) {
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    //funciones sass
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
    },
    //minificar con uglify
    uglify: {
      options: {
        mangle: false,
        compress: {
          drop_console: true
        }
      },
      js: {
        files: [{
          cwd: 'scripts/',  // ruta de nuestro javascript fuente
          expand: true,    // ingresar a las subcarpetas
          src: '*.js',     // patrón relativo a cwd
          dest: 'scripts/min/'  // destino de los archivos compresos
        }]
      }
    }
  });
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-sass');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.registerTask('default',['watch']);
}