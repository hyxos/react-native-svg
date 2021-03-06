import React from 'react';
import { requireNativeComponent } from 'react-native';
import { extract } from '../lib/extract/extractProps';
import Shape from './Shape';

export default class Path extends Shape<{
  d?: string;
}> {
  static displayName = 'Path';

  render() {
    const { props } = this;
    return (
      <RNSVGPath ref={this.refMethod} {...extract(this, props)} d={props.d} />
    );
  }
}

export const RNSVGPath = requireNativeComponent('RNSVGPath');
