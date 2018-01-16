package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.animation.Keyframe;

import java.util.List;

public abstract class KeyframeAnimation<T> extends BaseKeyframeAnimation<T, T> {
  KeyframeAnimation(List<? extends Keyframe<T>> keyframes) {
    super(keyframes);
  }
}
