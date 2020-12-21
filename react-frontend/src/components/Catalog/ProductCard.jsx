import {Button, Card, Container, Paper} from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import CardMedia from '@material-ui/core/CardMedia';
import CardContent from '@material-ui/core/CardContent';
import CardActions from '@material-ui/core/CardActions';
import Typography from '@material-ui/core/Typography';
import box from './box.png';
import React from 'react';
// const useStyles = makeStyles((theme) => ({
//     root: {
//       display: 'flex',
//       flexWrap: 'wrap',
//       '& > *': {
//         margin: theme.spacing(1),
//         width: theme.spacing(24),
//         height: theme.spacing(24),
//       },
//     },
//   }));
  const useStyles = makeStyles((theme) => ({
	root: {
	  maxWidth: 200,
	  minWidth:100,
	  display: 'flex',
	  flexWrap: 'wrap',
	  height:320,
	  '& > *': {
		margin: theme.spacing(1),
		width: theme.spacing(24),
		height: theme.spacing(24),
	  },
	},
	media: {
		height:150,
	},
	
  }));
export default function ProductCard(props) {
	const classes = useStyles();
	const [count, setCount] = React.useState(props.product.count);
	const [startcount, setstartCount] = React.useState(props.product.count);
	const [color, setColor] = React.useState(false);
	const [color2, setColor2] = React.useState(true);
    const addToCart = (product) => {
		let c = count;
		setColor2(false);
		console.log(c);
		props.setOrderState([...props.orderState,product]);
	
		setCount(count-1);
		c=c-1;
		console.log(c);
		if(c == 0) {
			setColor(true);
		}
        
    }
    const removeToCart = (product) => {
		setColor(false);
		setCount(count+1);
		if(count == startcount){
			setColor2(true);
		}
      let index = props.orderState.indexOf(product);
     
      props.orderState.splice(index,1)
      props.setOrderState([...props.orderState]);
    }
    return(
        <div className={classes.root}>
			
            <Paper elevation={4}>
			<Card className={classes.root}>
			<CardMedia
				className={classes.media}
				image={box}
				
			/>
			<CardContent>
				<Typography variant="h5" align="center">
				{props.product.name}
				</Typography>
				<Typography variant="h6" align="center">
				{props.product.price + "$"}
				</Typography>
			</CardContent>
		
			<CardActions style={{marginTop:"-230px"}}>
				<Button disabled = {color} variant="contained" color="primary" style={{width:"45%",
                                                                    margin:"3px",
                                                                    verticalAlign:"bottom"}}
                                                                    onClick={() =>addToCart(props.product)}>
                  Add
                </Button>
                <Button disabled = {color2} variant="contained" color="primary" style={{width:"45%",
                                                                    margin:"3px",
                                                                    verticalAlign:"bottom"
                                                                    }}
                                                                    onClick={() => removeToCart(props.product)}>
                  Remove
                </Button>
			</CardActions>
			</Card>
            </Paper>
        </div>
        
    );
}