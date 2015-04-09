
tinymce.create('tinymce.plugins.tabs', {  
	init: function(editor, url) {  
		editor.addButton('tabs', {
			title: 'Add a Tab Container',
			image: url + '/tab.png',
			onclick: function() {
				editor.selection.setContent('[tabs titles="A;B;C"]' + editor.selection.getContent() + '[/tabs]');  
			}
		});
	},
	createControl: function(n, cm) {
		return null;  
	},
});
tinymce.PluginManager.add('tabs', tinymce.plugins.tabs);  

tinymce.create('tinymce.plugins.tab', {  
	init: function(editor, url) {
		editor.addButton('tab', {
			title: 'Add a Tab',  
			image: url + '/tab.png',
			onclick: function() {
				editor.selection.setContent('[tab]' + editor.selection.getContent() + '[/tab]');  
			}
		});
	},
	createControl: function(n, cm) {
		return null;  
	},
});
tinymce.PluginManager.add('tab', tinymce.plugins.tab);  

tinymce.create('tinymce.plugins.horizontal_tabs', {  
	init: function(editor, url) {
		editor.addButton('horizontal_tabs', {
			title: 'Add a Horizontal Tab Container',
			image: url + '/tab.png',
			onclick: function() {
				editor.selection.setContent('[horizontal_tabs titles="1:A;2:B;3:C"]' + editor.selection.getContent() + '[/horizontal_tabs]');  
			}
		});
	},
	createControl: function(n, cm) {
		return null;  
	},
});
tinymce.PluginManager.add('horizontal_tabs', tinymce.plugins.horizontal_tabs);  

tinymce.create('tinymce.plugins.horizontal_tab', {  
	init: function(editor, url) {
		editor.addButton('horizontal_tab', {
			title: 'Add a Horizontal Tab',
			image: url + '/tab.png',
			onclick: function() {
				editor.selection.setContent('[horizontal_tab]' + editor.selection.getContent() + '[/horizontal_tab]');
			}
		});
	},
	createControl: function(n, cm) {
		return null;  
	},
});
tinymce.PluginManager.add('horizontal_tab', tinymce.plugins.horizontal_tab);  

tinymce.create('tinymce.plugins.toggle', {  
	init: function(editor, url) {  
		editor.addButton('toggle', {  
			title: 'Add a Toggle',  
			image: url + '/toggle.png',  
			onclick: function() {  
				editor.selection.setContent('[toggle title="Toggle"]' + editor.selection.getContent() + '[/toggle]');  
			}
		});
	},
	createControl: function(n, cm) {
		return null;  
	},
});
tinymce.PluginManager.add('toggle', tinymce.plugins.toggle);  

tinymce.create('tinymce.plugins.flyouts', {  
	init: function(editor, url) {  
		editor.addButton('flyouts', {  
			title: 'Add a Flyout Container',  
			image: url + '/flyout.png',  
			onclick: function() {  
				editor.selection.setContent('[flyouts]' + editor.selection.getContent() + '[/flyouts]');  
			}
		});
	},
	createControl: function(n, cm) {
		return null;  
	},
});
tinymce.PluginManager.add('flyouts', tinymce.plugins.flyouts);  

tinymce.create('tinymce.plugins.flyout', {  
	init: function(editor, url) {  
		editor.addButton('flyout', {  
			title: 'Add a Flyout',  
			image: url + '/flyout.png',  
			onclick: function() {  
				editor.selection.setContent('[flyout]' + editor.selection.getContent() + '[/flyout]');  
			}
		});
	},
	createControl: function(n, cm) {
		return null;  
	},
});
tinymce.PluginManager.add('flyout', tinymce.plugins.flyout);  

<?php
$buttonTypes = array('small', 'medium', 'large');
foreach ($buttonTypes as $buttonType) {
	?>
	tinymce.create('tinymce.plugins.<?php echo $buttonType ?>_button', {  
		init: function(editor, url) {  
			editor.addButton('<?php echo $buttonType ?>_button', {  
				title: 'Add Button: <?php echo ucfirst($buttonType) ?>',  
				image: url + '/button.png',  
				onclick: function() {
					editor.selection.setContent('[<?php echo $buttonType ?>_button url="#"]' + editor.selection.getContent() + '[/<?php echo $buttonType ?>_button]');  
				}
			});
		},
		createControl: function(n, cm) {
			return null;
		},
	});
	tinymce.PluginManager.add('<?php echo $buttonType ?>_button', tinymce.plugins.<?php echo $buttonType ?>_button);
	<?php
}
?>

tinymce.create('tinymce.plugins.button', {  
	init: function(editor, url) {  
		editor.addButton('button', {  
			title: 'Add a Colored Button',  
			image: url + '/button.png',  
			onclick: function() {  
				editor.selection.setContent('[button color="blue|green|red|orange|yellow|grey|black" url="#"]' + editor.selection.getContent() + '[/button]');  
			}
		});
	},
	createControl: function(n, cm) {
		return null;  
	},
});
tinymce.PluginManager.add('button', tinymce.plugins.button);  

(function() {  
	tinymce.create('tinymce.plugins.map', {  
		init: function(editor, url) {  
			editor.addButton('map', {  
				title: 'Add a Map',  
				image: url + '/map.png',  
				onclick: function() {  
					editor.selection.setContent('[map address="" latitude="" longitude=""]');  
				}
			});
		},
		createControl: function(n, cm) {
			return null;  
		},
	});
	tinymce.PluginManager.add('map', tinymce.plugins.map);  
})();

(function() {  
	tinymce.create('tinymce.plugins.dropcap', {  
		init: function(editor, url) {  
			editor.addButton('dropcap', {  
				title: 'Add a DropCap',  
				image: url + '/dropcap.png',  
				onclick: function() {  
					editor.selection.setContent('[dropcap]' + editor.selection.getContent() + '[/dropcap]');  
				}
			});
		},
		createControl: function(n, cm) {
			return null;  
		},
	});
	tinymce.PluginManager.add('dropcap', tinymce.plugins.dropcap);  
})();

(function() {  
	tinymce.create('tinymce.plugins.intro_slider', {  
		init: function(editor, url) {  
			editor.addButton('intro_slider', {
				title: 'Add an Intro Slider',
				image: url + '/slider.png',
				onclick: function() {
					editor.selection.setContent('[intro_slider]' + editor.selection.getContent() + '[/intro_slider]');
				}
			});
		},
		createControl: function(n, cm) {
			return null;  
		},
	});
	tinymce.PluginManager.add('intro_slider', tinymce.plugins.intro_slider);  
})();
(function() {  
	tinymce.create('tinymce.plugins.intro_slider_element', {  
		init: function(editor, url) {  
			editor.addButton('intro_slider_element', {  
				title: 'Add an Intro Slider Element',
				image: url + '/slider.png',
				onclick: function() {
					editor.selection.setContent('[intro_slider_element image="" title="Intro Slider Element"]' + editor.selection.getContent() + '[/intro_slider_element]');
				}
			});
		},
		createControl: function(n, cm) {
			return null;  
		},
	});
	tinymce.PluginManager.add('intro_slider_element', tinymce.plugins.intro_slider_element);  
})();

(function() {  
	tinymce.create('tinymce.plugins.client_slider', {  
		init: function(editor, url) {  
			editor.addButton('client_slider', {
				title: 'Add a Client Slider',
				image: url + '/slider.png',
				onclick: function() {
					editor.selection.setContent('[client_slider title="Our Clients"]' + editor.selection.getContent() + '[/client_slider]');
				}
			});
		},
		createControl: function(n, cm) {
			return null;  
		},
	});
	tinymce.PluginManager.add('client_slider', tinymce.plugins.client_slider);  
})();

(function() {  
	tinymce.create('tinymce.plugins.recent_work_slider', {  
		init: function(editor, url) {  
			editor.addButton('recent_work_slider', {
				title: 'Add a Recent Work Slider',
				image: url + '/slider.png',
				onclick: function() {
					editor.selection.setContent('[recent_work_slider title="Recent Work"]' + editor.selection.getContent() + '[/recent_work_slider]');
				}
			});
		},
		createControl: function(n, cm) {
			return null;  
		},
	});
	tinymce.PluginManager.add('recent_work_slider', tinymce.plugins.recent_work_slider);  
})();

<?php

global $construct;

foreach (array('FaceBook', 'Twitter', 'LinkedIn', 'RSS', 'Dribbble', 'Google') as $social_network) {
	?>
	(function() {  
		tinymce.create('tinymce.plugins.<?php echo 'social_' . strtolower($social_network) ?>', {  
			init: function(editor, url) {  
				editor.addButton('<?php echo 'social_' . strtolower($social_network) ?>', {
					title: 'Add a <?php echo $social_network ?> Icon',
					image: url + '/share.png',
					onclick: function() {
						editor.selection.setContent('[<?php echo 'social_' . strtolower($social_network) ?> size="small|large" style="1|2" url="#"]');
					}
				});
			},
			createControl: function(n, cm) {
				return null;  
			},
		});
		tinymce.PluginManager.add('<?php echo 'social_' . strtolower($social_network) ?>', tinymce.plugins.<?php echo 'social_' . strtolower($social_network) ?>);  
	})();
	<?php
}

?>

(function() {  
	tinymce.create('tinymce.plugins.quote', {  
		init: function(editor, url) {  
			editor.addButton('quote', {  
				title: 'Add a Quote Box',  
				image: url + '/quote.png',  
				onclick: function() {  
					editor.selection.setContent('[quote title="TITLE" url="#"]' + editor.selection.getContent() + '[/quote]');  
				}
			});
		},
		createControl: function(n, cm) {
			return null;  
		},
	});
	tinymce.PluginManager.add('quote', tinymce.plugins.quote);  
})();

(function() {  
	tinymce.create('tinymce.plugins.share', {  
		init: function(editor, url) {  
			editor.addButton('share', {  
				title: 'Add a Share Box',  
				image: url + '/share.png',  
				onclick: function() {  
					editor.selection.setContent('[share title="optional"]');  
				}
			});
		},
		createControl: function(n, cm) {
			return null;  
		},
	});
	tinymce.PluginManager.add('share', tinymce.plugins.share);  
})();

<?php
$iconSizes = array('small', 'medium', 'large');
foreach ($iconSizes as $iconSize) {
	?>
	tinymce.create('tinymce.plugins.<?php echo $iconSize ?>_icons', {  
		init: function(editor, url) {  
			editor.addButton('<?php echo $iconSize ?>_icons', {  
				title: 'Add Icon Container: <?php echo ucfirst($iconSize) ?>',  
				image: url + '/icons.png',  
				onclick: function() {
					editor.selection.setContent('[<?php echo $iconSize ?>_icons]' + editor.selection.getContent() + '[/<?php echo $iconSize ?>_icons]');  
				}
			});
		},
		createControl: function(n, cm) {
			return null;
		},
	});
	tinymce.PluginManager.add('<?php echo $iconSize ?>_icons', tinymce.plugins.<?php echo $iconSize ?>_icons);
	<?php
}
?>

tinymce.create('tinymce.plugins.icon', {  
	init: function(editor, url) {  
		editor.addButton('icon', {  
			title: 'Add Icon',  
			image: url + '/icon.png',  
			onclick: function() {
				editor.selection.setContent('[icon image="" url="#"]');  
			}
		});
	},
	createControl: function(n, cm) {
		return null;
	},
});
tinymce.PluginManager.add('icon', tinymce.plugins.icon);

<?php
$videoTypes = array('youtube', 'vimeo');
foreach ($videoTypes as $videoType) {
	?>
	tinymce.create('tinymce.plugins.<?php echo $videoType ?>', {  
		init: function(editor, url) {  
			editor.addButton('<?php echo $videoType ?>', {  
				title: 'Add <?php echo ucfirst($videoType) ?> Video',  
				image: url + '/<?php echo $videoType ?>.png',  
				onclick: function() {
					editor.selection.setContent('[<?php echo $videoType ?> id="#" width="960" height="360"]');  
				}
			});
		},
		createControl: function(n, cm) {
			return null;
		},
	});
	tinymce.PluginManager.add('<?php echo $videoType ?>', tinymce.plugins.<?php echo $videoType ?>);
	<?php
}
?>

<?php
$boxTypes = array('', 'success', 'error', 'warning');
foreach ($boxTypes as $boxType) {
	?>
	tinymce.create('tinymce.plugins.<?php echo $boxType ? $boxType . '_' : '' ?>box', {  
		init: function(editor, url) {  
			editor.addButton('<?php echo $boxType ? $boxType . '_' : '' ?>box', {  
				title: 'Add Box<?php echo $boxType ? ' :' . ucfirst($boxType) : '' ?>',  
				image: url + '/box.png',  
				onclick: function() {
					editor.selection.setContent('[<?php echo $boxType ? $boxType . '_' : '' ?>boxbox url="#"][/<?php echo $boxType ? $boxType . '_' : '' ?>boxbox]');  
				}
			});
		},
		createControl: function(n, cm) {
			return null;
		},
	});
	tinymce.PluginManager.add('<?php echo $boxType ? $boxType . '_' : '' ?>boxbox', tinymce.plugins.<?php echo $boxType ? $boxType . '_' : '' ?>boxbox);
	<?php
}
?>


