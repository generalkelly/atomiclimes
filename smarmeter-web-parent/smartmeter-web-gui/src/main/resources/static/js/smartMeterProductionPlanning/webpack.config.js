const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CleanWebpackPlugin = require('clean-webpack-plugin');
const MiniCssExtractPlugin = require("mini-css-extract-plugin");

module.exports = {
    entry : {
	time : './src/js/time.js',
	calendar : './src/js/calendar.js',
	submitProductionButton : './src/js/submitProductionButton.js'
    },
    output : {
	filename : '[name].js',
	path : path.resolve(__dirname, 'dist')
    },
    module : {
	rules : [ {
	    test : /\.css$/,
	    use : [ MiniCssExtractPlugin.loader, 'css-loader' ]
	}, {
	    test : /\.(ttf|woff|eot)$/i,
	    use : [ 'url-loader' ]
	}, {
	    test : /\.(png|jpg|gif|svg)$/i,
	    use : [ {
		loader : 'url-loader',
		options : {
		    limit : 8192
		}
	    } ]
	} ],
    },
    plugins : [ new CleanWebpackPlugin([ 'dist' ]), new HtmlWebpackPlugin({
	title : 'Output Management',
	template : './src/html/index.html'
    }), new MiniCssExtractPlugin({
	filename : 'time.css',
    }) ]
};
